package cf.backspace.plugins.customcrafting;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 23.04.2016.
 */
public class CustomCrafting extends JavaPlugin implements Listener {

    private static CustomCrafting api;

    public static CustomCrafting getAPI() {
        return api;
    }

    private List<CustomRecipe> recipes;
    private List<CustomShapelessRecipe> shapelessRecipes;

    private void debug() {
        List<ItemStack> matrix = new ArrayList<ItemStack>();
        ItemStack item = new ItemStack(Material.STONE,2);
        ItemStack res = new ItemStack(Material.LOG,4);
        for(int i = 0; i <8; i++) matrix.add(item);
        addRecipe(new CustomRecipe(matrix,res));
    }

    @Override
    public void onEnable() {
        api = this;
        recipes = new ArrayList();
        shapelessRecipes = new ArrayList();
        getServer().getPluginManager().registerEvents(this,this);
        debug();
    }

    @Override
    public void onDisable() {

    }

    public void addRecipe(CustomRecipe recipe) {
        recipes.add(recipe);
    }

    public void addRecipe(CustomShapelessRecipe recipe) {
        shapelessRecipes.add(recipe);
    }

    @EventHandler
    public void on(InventoryClickEvent e){
        if (e.getInventory().getType() == InventoryType.WORKBENCH) {
            CraftingInventory inv = (CraftingInventory) e.getInventory();
            List<ItemStack> matrix = new ArrayList();
            ItemStack result = null;
            for(ItemStack i : inv.getMatrix()) {
                matrix.add(i);
            }
            for(CustomRecipe recipe : recipes) {
                if(recipe.doesFit(matrix)) {
                    System.out.println("Test");
                    result = recipe.getResult();
                }
            }
            if(result == null) {
                for(CustomShapelessRecipe recipe : shapelessRecipes) {
                    if(recipe.doesFit(matrix)) {
                        result = recipe.getResult();
                    }
                }
            }

            if(result != null) {
                boolean hasRes = false;
                try {hasRes = inv.getResult() != null;} catch(Exception ex) {}
                if((hasRes && !inv.getResult().equals(result)) || !hasRes) {
                    inv.setResult(result);
                    inv.setMatrix(inv.getMatrix());
                    ((Player) e.getWhoClicked()).updateInventory();
                }
            }
        }
    }

}
