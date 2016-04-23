package cf.backspace.plugins.customcrafting;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Paul on 23.04.2016.
 */
public class CustomShapelessRecipe {

    private List<ItemStack> matrix;
    private ItemStack result;

    public CustomShapelessRecipe(List<ItemStack> matrix, ItemStack result) {
        this.matrix = matrix;
        this.result = result;
    }

    public boolean doesFit(List<ItemStack> newMatrix) {
        for(int i = 0; i<matrix.size(); i++) {
            if(!newMatrix.contains(matrix.get(i))) return false;
        }
        return true;
    }

    public List<ItemStack> getMatrix() {
        return matrix;
    }

    public ItemStack getResult() {
        return result;
    }

}
