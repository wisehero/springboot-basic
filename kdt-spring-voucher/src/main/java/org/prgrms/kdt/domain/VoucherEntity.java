package org.prgrms.kdt.domain;

import lombok.Getter;
import lombok.Setter;
import org.prgrms.kdt.enumType.VoucherStatus;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class VoucherEntity {

    private UUID voucherId;
    private Enum<VoucherStatus> voucherType;
    private Optional<Long> discount;

    public VoucherEntity(UUID voucherId, Enum<VoucherStatus> voucherType, long discount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discount = Optional.of(discount);
    }
}