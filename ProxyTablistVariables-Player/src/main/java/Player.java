import eu.scrayos.proxytablist.api.Variable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Player implements Variable {
    private final static Pattern pattern = Pattern.compile("\\{player\\}");
    private int lastRefreshId = -1;
    private Iterator<ProxiedPlayer> playerIterator;
    private int lastSlot;

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    public String formatName(ProxiedPlayer p) {
        StringBuilder name = new StringBuilder();

        //Check for Prefix
        /*if(ProxyTablist.getInstance().getConfig().contains("variable.player.prefix." + p.getName())) {
            name.append(ProxyTablist.getInstance().getConfig().getString("variable.player.prefix." + p.getName(), ""));
        }*/

        String last = null;

        for (String c : new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "l", "m", "n", "o", "k", "r"}) {
            last = (p.hasPermission("proxy.tablist." + c)) ? c : null;
        }

        if (last != null) {
            name.append("§");
            name.append(last);
        }

        name.append(p.getName());
        return name.toString();
    }

    @Override
    public String getText(String foundString, int refreshId, Short ping, ProxiedPlayer proxiedPlayer, Boolean global, int slot) {
        if (lastRefreshId != refreshId) {
            lastRefreshId = refreshId;
            playerIterator = BungeeCord.getInstance().getPlayers().iterator();
        }

        if (lastSlot == slot) {
            global = false;
            return "";
        } else {
            lastSlot = slot;
        }

        /*if(!proxyTablist.getConfig().contains("variable.player.prefix")) {
            proxyTablist.getConfig().set("variable.player.prefix", new HashMap<String, String>());
        } */

        if (!playerIterator.hasNext()) {
            return "";
        }

        ProxiedPlayer player = playerIterator.next();
        ping = (new Integer(player.getPing())).shortValue();

        return formatName(player);
    }
}
