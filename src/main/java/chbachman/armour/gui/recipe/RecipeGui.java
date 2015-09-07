package chbachman.armour.gui.recipe;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.Recipe;
import chbachman.api.util.ImmutableArray;
import chbachman.armour.gui.GuiHelper;
import chbachman.armour.gui.element.TabCompatible;
import chbachman.armour.gui.element.TabRecipeList;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;
import chbachman.armour.reference.Reference;
import cofh.core.gui.GuiBaseAdv;
import cofh.core.network.PacketHandler;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.gui.element.ElementTextField;
import cofh.lib.util.helpers.StringHelper;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntArray;

public class RecipeGui extends GuiBaseAdv {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURE_LOCATION + "/gui/recipeGui.png");

    public RecipeContainer container;

    public ElementButton rightArrow;
    public ElementButton leftArrow;
    public ElementButton upgrade;
    public ElementTextField field;

    public TabCompatible compatible;
    public TabRecipeList list;

    public int index = 0;

    public static final ImmutableArray<Recipe> recipes = UpgradeRegistry.getRecipeList();

    public IntArray indicies = new IntArray();

    public RecipeGui(RecipeContainer container, InventoryPlayer inventory) {
        super(container, TEXTURE);

        this.container = container;

        this.texture = TEXTURE;
        this.drawTitle = false;
        this.drawInventory = false;
        this.xSize = 176;
        this.ySize = 172;

        this.leftArrow = new ElementButton(this, 5, 25, "Go Back", 227, 12, 227, 12, 227, 12, 7, 7, TEXTURE.toString());
        this.rightArrow = new ElementButton(this, 164, 25, "Next", 235, 12, 235, 12, 235, 12, 7, 7, TEXTURE.toString());

        this.upgrade = new ElementButton(this, 71, 38, "Upgrade", 71, 38, 71, 38, 16, 16, TEXTURE.toString());
        this.upgrade.setToolTip("Add Upgrade?");

        this.field = new ElementTextField(this, 7, 6, 162, 11) {

            @Override
            protected void onCharacterEntered(boolean success) {
                if (success) {
                    handleTyping(this.getText());
                }
            }
        };

        field.setBackgroundColor(0xFF000000, 0xFF000000, 0xFF000000);

        this.list = new TabRecipeList(this, indicies) {

            @Override
            public void onUpgradeSelected(IUpgrade upgrade, int index) {
                RecipeGui.this.index = index;
                syncAndWrapIndex();
            }

        };

        this.compatible = new TabCompatible(this, this.container);

        this.rightArrow.setToolTip("Next");
        this.leftArrow.setToolTip("Back");
    }

    @Override
    public void initGui() {
        super.initGui();

        this.addTab(this.compatible);
        this.addTab(this.list);

        this.addElement(this.leftArrow);
        this.addElement(this.rightArrow);
        this.addElement(this.upgrade);
        this.addElement(this.field);

        // this.addElement(new ElementItem(this, 180,
        // 60).setItem(Vanilla.bootsModular));

        handleTyping("");

        // this.compatible.displaySlots(false);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {
        super.drawGuiContainerBackgroundLayer(partialTick, x, y);

        if (this.container.recipe != null) {

            IUpgrade upgrade = this.container.recipe.getRecipeOutput();

            GuiHelper.drawStringBounded(this, StringHelper.localize(upgrade.getName()), 70, this.guiLeft + 100, this.guiTop + 38, 0xFFFFFF);

            GuiHelper.drawStringBounded(this, StringHelper.localize(upgrade.getInformation()), 159, this.guiLeft + 11, this.guiTop + 100, 0xFFFFFF);
        }
    }

    public void handleTyping(String text) {
        indicies.clear();

        if (text.isEmpty()) {

            for (int i = 0; i < recipes.size(); i++) {
                indicies.add(i);
            }

        } else {
            text = text.toLowerCase();

            for (int i = 0; i < recipes.size(); i++) {

                String name = StringHelper.localize(recipes.get(i).getRecipeOutput().getName()).toLowerCase();

                if (name.contains(text)) {
                    indicies.add(i);
                }
            }
        }

        this.list.updateList();

        this.syncAndWrapIndex();
    }

    @Override
    public void handleElementButtonClick(String buttonName, int mouseButton) {
        super.handleElementButtonClick(buttonName, mouseButton);

        if (buttonName.equals("Upgrade")) {
            PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString("Upgrade"));
        }

        if (buttonName.equals("Go Back")) {
            this.index--;
        } else if (buttonName.equals("Next")) {
            this.index++;
        }

        syncAndWrapIndex();
    }

    private void syncAndWrapIndex() { // Wraps the index and syncs with the
                                      // container.

        if (this.indicies.size == 0) {
            this.index = -1;
            this.container.updateIndex(index);
            PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString(" ").addInt(this.index));
        } else {
            this.index = MathUtils.clamp(this.index, 0, this.indicies.size - 1);
            this.container.updateIndex(this.indicies.get(index));
            PacketHandler.sendToServer(ArmourPacket.getPacket(PacketTypes.BUTTON).addString(" ").addInt(this.indicies.get(this.index)));
        }

    }

    @Override
    public void keyTyped(char characterTyped, int keyPressed) {
        if (keyPressed == Keyboard.KEY_ESCAPE) {
            PacketHandler.sendToServer(ArmourPacket.getPacket(ArmourPacket.PacketTypes.KEYTYPED).addShort((short) characterTyped).addInt(keyPressed));
            return;
        }
        super.keyTyped(characterTyped, keyPressed);
    }

}
