package me.zuyte.admin.events;

import me.zuyte.admin.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Damage implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            if (Main.getInstance().toystick.containsKey(e.getEntity().getName())) {
                e.setCancelled(true);
                Main.getInstance().toystick.remove(e.getEntity().getName());
            }
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
        if (Main.getInstance().kaboom.containsKey(e.getEntity().getName())) {
            e.setCancelled(true);
            Main.getInstance().kaboom.remove(e.getEntity().getName());
            } else if (Main.getInstance().toystick.containsKey(e.getEntity().getName())) {
            Main.getInstance().toystick.remove(e.getEntity().getName());
        }
        }
    }
}
