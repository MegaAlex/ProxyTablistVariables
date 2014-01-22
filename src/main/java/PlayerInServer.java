import eu.scrayos.proxytablist.objects.Variable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class PlayerInServer implements Variable {
    private HashMap<ServerInfo, Iterator<ProxiedPlayer>> serverPlayerList = new HashMap<ServerInfo, Iterator<ProxiedPlayer>>();
    private static final String pattern = "playerInServer:([\\w]+)";
    private int lastRefreshId = -1;

    @Override
    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }

    @Override
    public String getText(String foundString, int refreshId) {
        if(lastRefreshId != refreshId) {
            lastRefreshId = refreshId;
            serverPlayerList.clear();
        }

        String server = foundString.substring(foundString.indexOf(":") + 1);

        ServerInfo serverInfo = BungeeCord.getInstance().getServerInfo(server);
        if(serverInfo == null) {
            return "Error";
        }

        if(!serverPlayerList.containsKey(serverInfo)) {
            serverPlayerList.put(serverInfo, serverInfo.getPlayers().iterator());
        }

        if(!serverPlayerList.get(serverInfo).hasNext()) {
            return "";
        }

        return serverPlayerList.get(serverInfo).next().getName();
    }
}
