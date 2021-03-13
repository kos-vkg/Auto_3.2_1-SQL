package ru.netology.data;

import lombok.Value;

public class DataHelper {
    public DataHelper() {
    }


    @Value
    static class CardInfo {
        private String codeCard;
    }

    @Value
    public static class AuthInfo {
        private String id;
        private String login;
        private String password;
        private String passCode;

    }

    static String p1 = "$2a$10$HwcFKUs4qK.0ci/sczNrs.wrFgyl8ueJ4guLEsj1CbuFGf290JJEC"; //"123qwerty"
    static String v1 = "$2a$10$juZ3z4hmOMnAjKfE.Y0kB.IVujBMeAMOTaGO4mO8jgklXoP.myzlq"; //"qwerty123"

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("1", "vasya", "qwerty123",v1);
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("2", "petya","123qwerty", p1);
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

}
