package se.jrp.playertrading.resources;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
 
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
 
/*
* A serializable ItemStack
*/
public class CardboardBox implements Serializable {
    private static final long serialVersionUID = 729890133797629668L;
	
	private final Material type;
    private final int amount;
    private final short damage;
    private final byte data;
 
    private final HashMap<CardboardEnchantment, Integer> enchants;
 
    public CardboardBox(ItemStack item) {
        this.type = item.getType();
        this.amount = item.getAmount();
        this.damage = item.getDurability();
        this.data = item.getData().getData();
        HashMap<CardboardEnchantment, Integer> map = new HashMap<>();
        Map<Enchantment, Integer> enchantments = item.getEnchantments();
        for(Enchantment enchantment : enchantments.keySet()) {
            map.put(new CardboardEnchantment(enchantment), enchantments.get(enchantment));
        }
        this.enchants = map;
    }
 
    public ItemStack unbox() {
        ItemStack item = new ItemStack(type, amount, damage, data);
        HashMap<Enchantment, Integer> map = new HashMap<>();
        for(CardboardEnchantment cEnchantment : enchants.keySet()) {
            map.put(cEnchantment.unbox(), enchants.get(cEnchantment));
        }
        item.addUnsafeEnchantments(map);
        return item;
    }
}