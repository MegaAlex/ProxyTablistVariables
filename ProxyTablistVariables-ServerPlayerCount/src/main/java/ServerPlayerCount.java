import eu.scrayos.proxytablist.api.Variable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ServerPlayerCount implements Variable {
    private final static Pattern pattern = Pattern.compile("\\{serverCount:([\\w]+)\\}");
    private String server = null;

    private int lastSlot = -1;

    public ServerPlayerCount() {
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
    public void setRefreshId(int i) {

    }

    @Override
    public boolean hasUpdate(int slot, ProxiedPlayer proxiedPlayer) {
        if(slot != lastSlot) {
            lastSlot = slot;

            return true;
        }

        return false;
    }

    @Override
    public void setMatchResult(MatchResult matchResult) {
        server = matchResult.group(1);
    }

    @Override
    public boolean isForGlobalTablist() {
        return true;
    }

    @Override
    public String getText(Short aShort) {
        ServerInfo serverInfo = BungeeCord.getInstance().getServerInfo(server);
        if(serverInfo == null) {
            return "Error";
        }

        return String.valueOf(serverInfo.getPlayers().size());
    }
}
