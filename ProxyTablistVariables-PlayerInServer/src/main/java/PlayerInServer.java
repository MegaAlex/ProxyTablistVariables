import eu.scrayos.proxytablist.api.Variable;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class PlayerInServer implements Variable {
    private HashMap<String, Iterator<ProxiedPlayer>> serverPlayerList = new HashMap<String, Iterator<ProxiedPlayer>>();
    private static final Pattern pattern = Pattern.compile("\\{playerInServer:([\\w]+)\\}");
    private String server = null;
    private ProxiedPlayer player;
    private ServerInfo serverInfo;

    private int lastRefreshId = -1;
    private int lastSlot = -1;
    private int lastSlotUsed = -2;

    public String formatName(ProxiedPlayer p) {
        StringBuilder name = new StringBuilder();

        for (String c : new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "l", "m", "n", "o", "k", "r"}) {
            if (p.hasPermission("proxy.tablist." + c)) {
                name.append("§");
                name.append(c);
            }
        }

        name.append(p.getName());
        return name.toString();
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public void setRefreshId(int refreshId) {
        if (lastRefreshId != refreshId) {
            lastRefreshId = refreshId;
            serverPlayerList.clear();
        }
    }

    @Override
    public boolean hasUpdate(int slot, ProxiedPlayer proxiedPlayer) {
        if(slot != lastSlot) {
            lastSlot = slot;
        }
        return true;
    }

    @Override
    public void setMatchResult(MatchResult matchResult) {
        if( lastSlot == lastSlotUsed ) {
            return;
        }

        lastSlotUsed = lastSlot;
        server = matchResult.group(1);
        serverInfo = ProxyServer.getInstance().getServerInfo(server);
        if (serverInfo == null) {
            return;
        }

        if(!serverPlayerList.containsKey(serverInfo.getName())) {
            serverPlayerList.put(serverInfo.getName(), serverInfo.getPlayers().iterator());
        }

        if (!serverPlayerList.get(serverInfo.getName()).hasNext()) {
            player = null;
            return;
        }

        player = serverPlayerList.get(serverInfo.getName()).next();
     }

    @Override
    public boolean isGlobal() {
        return true;
    }

    @Override
    public String getText() {
        if(serverInfo == null) {
            return "Error";
        }

        if(player == null) {
            return "";
        }
        return formatName(player);
    }

    @Override
    public short getPing() {
        return (player != null) ? (short) player.getPing() : 0;
    }
}
