package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.VoucherType;
import org.prgrms.kdtspringdemo.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.Voucher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository, InitializingBean {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final String FILE_NAME = "voucher.csv";

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(FILE_NAME), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            String contents = voucher.saveCSV();
            writer.append(contents).append("\n");
            storage.put(voucher.getVoucherId(), voucher);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(FILE_NAME))) {
            reader.lines().forEach(line -> {
                String[] dataArray = line.split(",");
                String voucherType = dataArray[0];
                String uuid = dataArray[1];
                String data = dataArray[2];

                if (voucherType.equals("FixedAmountVoucher")) {
                    var voucher = new FixedAmountVoucher(UUID.fromString(uuid), "FixedAmountVoucherName", Long.parseLong(data), VoucherType.FIXED_AMOUNT, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
                    storage.put(voucher.getVoucherId(), voucher);
                } else if (voucherType.equals("PercentDiscountVoucher")) {
                    var voucher = new PercentDiscountVoucher(UUID.fromString(uuid), "PercentDiscountVoucherName", Long.parseLong(data), VoucherType.PERCENT_DISCOUNT, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
                    storage.put(voucher.getVoucherId(), voucher);
                } else {
                    System.out.println("None VoucherType!!! : " + voucherType);
                }
            });
        } catch (IOException e) {
            System.out.println("Doesn't exist file.");
        }
    }
}