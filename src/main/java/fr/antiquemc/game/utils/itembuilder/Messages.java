package fr.antiquemc.game.utils.itembuilder;


import org.bukkit.ChatColor;

import java.math.RoundingMode;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Messages {

    private static String BUKKIT_COLOR_CODE = "ยง";
    private static char ALT_COLOR_CODE = '&';

    public static String color(String s) {
        return (s != null) ? ChatColor.translateAlternateColorCodes('&', s) : null;
    }

    public static List<String> color(Iterable<? extends String> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .filter(Objects::nonNull)
                .map(Messages::color)
                .collect(Collectors.toList());
    }

    public static List<String> color(String... args) {
        return color(Arrays.asList(args));
    }

    public static String unColor(String s) {
        if(s == null) return null;
        StringBuilder builder = new StringBuilder(s.length());
        CharacterIterator it = new StringCharacterIterator(s);
        for(char c = it.first(); c != Character.MAX_VALUE; c = it.next())
            builder.append(c);
        return builder.toString();
    }

    public static List<String> unColor(Iterable<? extends String> iterable) {
        return (iterable == null) ? null : StreamSupport.stream(iterable.spliterator(), false)
                .filter(Objects::nonNull)
                .map(Messages::unColor)
                .collect(Collectors.toList());
    }

    public static List<String> unColor(String... args) {
        return unColor(Arrays.asList(args));
    }

    public static DecimalFormat getDecimal(int figure) {
        StringBuilder r = new StringBuilder();
        for(int i = 0; i < figure; i++)
            r.append("#");
        DecimalFormat df = new DecimalFormat("#." + r);
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        return df;
    }

    public static String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                        ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return com.google.common.base.Strings.repeat("" + completedColor + symbol, progressBars)
                + com.google.common.base.Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }
}