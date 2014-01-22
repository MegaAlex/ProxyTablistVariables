import eu.scrayos.proxytablist.objects.Variable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Player implements Variable {
    private final static Pattern pattern = Pattern.compile("player");
    private int lastRefreshId = -1;
    private Iterator<ProxiedPlayer> playerIterator;

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    public String formatName(ProxiedPlayer p) {
        String name = p.getName();
        for (String c : new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "l", "m", "n", "o", "k", "r"}) {
            name = (p.hasPermission("proxy.tablist." + c) ? "§" + c + name : name);
        }

        return name;
    }

    @Override
    public String getText(String foundString, int refreshId, Short ping) {
        if (lastRefreshId != refreshId) {
            lastRefreshId = refreshId;
            playerIterator = BungeeCord.getInstance().getPlayers().iterator();
        }

        if (!playerIterator.hasNext()) {
            return "";
        }

        ProxiedPlayer player = playerIterator.next();
        ping = (new Integer(player.getPing())).shortValue();

        return formatName(player);
    }
}
