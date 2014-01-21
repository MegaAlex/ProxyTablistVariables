package net.cubespace.ProxyTablistVariables;

import eu.scrayos.proxytablist.objects.Variable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ServerPlayerCount implements Variable {
    private final static Pattern pattern = Pattern.compile("serverCount:([\\w]+)");

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String getText(String foundString, int refreshId) {
        String server = pattern.matcher(foundString).group(1);

        ServerInfo serverInfo = BungeeCord.getInstance().getServerInfo(server);
        if(serverInfo == null) {
            return "Error";
        }

        return "" + serverInfo.getPlayers().size();
    }
}
