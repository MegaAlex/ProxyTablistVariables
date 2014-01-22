import eu.scrayos.proxytablist.objects.Variable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
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
    private static final Pattern pattern = Pattern.compile("playerInServer:([\\w]+)");
    private int lastRefreshId = -1;

    public PlayerInServer() {
        (new Thread() {
            public void run() {
                try {
                    for (ServerInfo serverInfo : BungeeCord.getInstance().getServers().values()) {
                        pingServer(serverInfo);
                    }

                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted ping Thread");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void pingServer(final ServerInfo serverInfo) {
        serverInfo.ping(new Callback<ServerPing>() {
            @Override
            public void done(ServerPing serverPing, Throwable throwable) {
                System.out.println("Pinged Server " + serverInfo.getName() + ". The Server holds " + serverPing.getPlayers().getOnline() + " Players");
            }
        });
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String getText(String foundString, int refreshId) {
        String server = foundString.substring(foundString.indexOf(":") + 1);

        if (lastRefreshId != refreshId) {
            lastRefreshId = refreshId;
            serverPlayerList.clear();
        }

        ServerInfo serverInfo = BungeeCord.getInstance().getServerInfo(server);
        if (serverInfo == null) {
            return "Error";
        }

        if (!serverPlayerList.containsKey(serverInfo)) {
            serverPlayerList.put(serverInfo, serverInfo.getPlayers().iterator());
        }

        if (!serverPlayerList.get(serverInfo).hasNext()) {
            return "";
        }

        return serverPlayerList.get(serverInfo).next().getName();
    }
}
