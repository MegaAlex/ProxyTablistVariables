package net.cubespace.ProxyTablistVariables;

import eu.scrayos.proxytablist.objects.Variable;
import net.md_5.bungee.BungeeCord;

import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class OverAllCount implements Variable {
    private final static Pattern pattern = Pattern.compile("overallCount");

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String getText(String foundString, int refreshId) {
        return String.valueOf(BungeeCord.getInstance().getPlayers().size());
    }
}
