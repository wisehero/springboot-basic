package org.prgrms.kdt.utill.IO;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherEntity;

import java.util.List;
import java.util.Scanner;

public class IoConsole implements Input, Output {

    private static Scanner sc = new Scanner(System.in);


    //IO console
    public static void init() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println("Type remove to delete all vouchers.");
    }


    public void exit() {
        System.out.println("bye bye~~");
    }


    @Override
    public String input() {
        return sc.nextLine();
    }

    @Override
    public String inputText(String s) {
        System.out.println(s);
        return sc.nextLine();
    }

    @Override
    public void inputError() {
        System.out.println("Invalid input. please input again!!");
    }

    ;

    @Override
    public void outputList(List<VoucherEntity> list) {
        list.stream().forEach(m -> System.out.println(m.toString()));
        if(list.size() == 0) System.out.println("출력 할 Voucher가 없습니다.");
        else System.out.println("생성한 Voucher는 총 : " + list.size() + "개 입니다.");
    }

    @Override
    public void outputListFile(List<String> list) {
        list.stream().forEach(System.out::println);
        System.out.println("생성한 Voucher는 총 : " + list.size() + "개 입니다.");
    }


    @Override
    public void message(String s) {
        System.out.println(s);
    }


}