package service;

public final class MoneyParser {
    private MoneyParser() {
    }

    public static int parseToCents(String text) {
        if (text == null) throw new IllegalArgumentException("Amount is required");

        String s = text.trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Amount is required");
        s = s.replace(',', '.');

        if (!s.matches("\\d+(\\.\\d{1,2})?")) {
            throw new IllegalArgumentException("Invalid amount format (use e.g. 12.34)");
        }
        String[] parts = s.split("\\.");
        int eur = Integer.parseInt(parts[0]);

        int cents = 0;
        if (parts.length == 2) {
            String frac = parts[1];
            if (frac.length() == 1) frac = frac + "0";
            cents = Integer.parseInt(frac);
        }

        return eur * 100 + cents;
    }
    public static String formatFromCents(int cents) {
        int abs = Math.abs(cents);
        int eur = abs / 100;
        int cent = abs % 100;
        String sign = cents < 0 ? "-" : "";
        return String.format("%s%d.%02d", sign, eur, cent);
    }

}

