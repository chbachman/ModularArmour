package chbachman.armour.items.armour.renderer;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class JetpackModel extends ModelBiped {

    // public ModelRenderer bipedHead;
    // public ModelRenderer bipedBody;
    // public ModelRenderer bipedRightArm;
    // public ModelRenderer bipedLeftArm;
    // public ModelRenderer bipedRightLeg;
    // public ModelRenderer bipedLeftLeg;

    public ModelRenderer JPDecorationPieceRight15;
    public ModelRenderer JPDecorationPieceLeft15;
    public ModelRenderer JPDecorationPieceRight14;
    public ModelRenderer JPDecorationPieceLeft14;
    public ModelRenderer JPDecorationPieceLeft13;
    public ModelRenderer JPDecorationPieceRight13;
    public ModelRenderer JPDecorationPieceLeft12;
    public ModelRenderer JPDecorationPieceLeft11;
    public ModelRenderer JPDecorationPieceRight12;
    public ModelRenderer JPDecorationPieceRight11;
    public ModelRenderer JPDecorationPieceRight10;
    public ModelRenderer JPDecorationPieceLeft10;
    public ModelRenderer JPDecorationPieceLeft9;
    public ModelRenderer JPDecorationPieceRight9;
    public ModelRenderer JPDecorationPieceLeft8;
    public ModelRenderer JPDecorationPieceLeft7;
    public ModelRenderer JPDecorationPieceRight8;
    public ModelRenderer JPDecorationPieceLeft6;
    public ModelRenderer JPDecorationPieceRight7;
    public ModelRenderer JPDecorationPieceLeft5;
    public ModelRenderer JPDecorationPieceRight6;
    public ModelRenderer JPDecorationPieceRight5;
    public ModelRenderer JPDecorationPiece2;
    public ModelRenderer JPDecorationPiece1;
    public ModelRenderer JPDecorationPieceRight4;
    public ModelRenderer JPDecorationPieceLeft4;
    public ModelRenderer JPDecorationPieceLeft3;
    public ModelRenderer JPDecorationPieceRight3;
    public ModelRenderer JPDecorationPieceLeft2;
    public ModelRenderer JPDecorationPieceRight2;
    public ModelRenderer JPDecorationPieceLeft1;
    public ModelRenderer JPDecorationPieceRight1;
    public ModelRenderer JetPackMainPieceLeft;
    public ModelRenderer JetPackMainPieceRight;

    public JetpackModel() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.JPDecorationPieceRight8 = new ModelRenderer(this, 252, 118);
        this.JPDecorationPieceRight8.setRotationPoint(-5.5F, 9.5F, 7.5F);
        this.JPDecorationPieceRight8.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.JPDecorationPieceLeft14 = new ModelRenderer(this, 238, 102);
        this.JPDecorationPieceLeft14.setRotationPoint(5.0F, 10.0F, 4.0F);
        this.JPDecorationPieceLeft14.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.JPDecorationPieceLeft11 = new ModelRenderer(this, 246, 122);
        this.JPDecorationPieceLeft11.setRotationPoint(1.0F, 10.0F, 8.0F);
        this.JPDecorationPieceLeft11.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.JPDecorationPieceLeft1 = new ModelRenderer(this, 244, 82);
        this.JPDecorationPieceLeft1.setRotationPoint(1.5F, 2.0F, 4.5F);
        this.JPDecorationPieceLeft1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.JPDecorationPieceLeft8 = new ModelRenderer(this, 238, 113);
        this.JPDecorationPieceLeft8.setRotationPoint(0.5F, 9.0F, 4.0F);
        this.JPDecorationPieceLeft8.addBox(0.0F, 0.0F, 0.0F, 5, 1, 4, 0.0F);
        this.JPDecorationPieceRight7 = new ModelRenderer(this, 252, 118);
        this.JPDecorationPieceRight7.setRotationPoint(-1.5F, 9.5F, 7.5F);
        this.JPDecorationPieceRight7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.JPDecorationPieceRight6 = new ModelRenderer(this, 238, 113);
        this.JPDecorationPieceRight6.setRotationPoint(-5.5F, 9.0F, 4.0F);
        this.JPDecorationPieceRight6.addBox(0.0F, 0.0F, 0.0F, 5, 1, 4, 0.0F);
        this.JPDecorationPieceLeft5 = new ModelRenderer(this, 252, 118);
        this.JPDecorationPieceLeft5.setRotationPoint(4.5F, 9.5F, 7.5F);
        this.JPDecorationPieceLeft5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.JPDecorationPieceRight10 = new ModelRenderer(this, 252, 118);
        this.JPDecorationPieceRight10.setRotationPoint(-1.5F, 9.5F, 3.5F);
        this.JPDecorationPieceRight10.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.JPDecorationPieceRight15 = new ModelRenderer(this, 238, 96);
        this.JPDecorationPieceRight15.setRotationPoint(-4.0F, 3.5F, 3.0F);
        this.JPDecorationPieceRight15.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.JPDecorationPieceRight1 = new ModelRenderer(this, 244, 82);
        this.JPDecorationPieceRight1.setRotationPoint(-4.5F, 2.0F, 4.5F);
        this.JPDecorationPieceRight1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.JPDecorationPieceRight9 = new ModelRenderer(this, 252, 118);
        this.JPDecorationPieceRight9.setRotationPoint(-5.5F, 9.5F, 3.5F);
        this.JPDecorationPieceRight9.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.JPDecorationPieceRight11 = new ModelRenderer(this, 246, 122);
        this.JPDecorationPieceRight11.setRotationPoint(-5.0F, 10.0F, 8.0F);
        this.JPDecorationPieceRight11.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.JPDecorationPieceRight5 = new ModelRenderer(this, 238, 107);
        this.JPDecorationPieceRight5.setRotationPoint(-5.0F, 9.0F, 3.5F);
        this.JPDecorationPieceRight5.addBox(0.0F, 0.0F, 0.0F, 4, 1, 5, 0.0F);
        this.JetPackMainPieceLeft = new ModelRenderer(this, 240, 71);
        this.JetPackMainPieceLeft.setRotationPoint(1.0F, 2.5F, 4.0F);
        this.JetPackMainPieceLeft.addBox(0.0F, 0.0F, 0.0F, 4, 7, 4, 0.0F);
        this.JPDecorationPieceRight13 = new ModelRenderer(this, 238, 102);
        this.JPDecorationPieceRight13.setRotationPoint(-1.0F, 10.0F, 4.0F);
        this.JPDecorationPieceRight13.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.JPDecorationPieceLeft10 = new ModelRenderer(this, 252, 118);
        this.JPDecorationPieceLeft10.setRotationPoint(4.5F, 9.5F, 3.5F);
        this.JPDecorationPieceLeft10.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.JPDecorationPieceLeft12 = new ModelRenderer(this, 246, 122);
        this.JPDecorationPieceLeft12.setRotationPoint(1.0F, 10.0F, 3.0F);
        this.JPDecorationPieceLeft12.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.JPDecorationPieceLeft9 = new ModelRenderer(this, 252, 118);
        this.JPDecorationPieceLeft9.setRotationPoint(0.5F, 9.5F, 3.5F);
        this.JPDecorationPieceLeft9.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.JPDecorationPieceRight2 = new ModelRenderer(this, 252, 86);
        this.JPDecorationPieceRight2.setRotationPoint(-3.5F, 1.5F, 5.5F);
        this.JPDecorationPieceRight2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.JPDecorationPiece2 = new ModelRenderer(this, 250, 103);
        this.JPDecorationPiece2.setRotationPoint(-1.0F, 4.5F, 5.5F);
        this.JPDecorationPiece2.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
        this.JPDecorationPiece1 = new ModelRenderer(this, 248, 99);
        this.JPDecorationPiece1.setRotationPoint(-1.0F, 5.0F, 5.0F);
        this.JPDecorationPiece1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.JetPackMainPieceRight = new ModelRenderer(this, 240, 71);
        this.JetPackMainPieceRight.setRotationPoint(-5.0F, 2.5F, 4.0F);
        this.JetPackMainPieceRight.addBox(0.0F, 0.0F, 0.0F, 4, 7, 4, 0.0F);
        this.JPDecorationPieceRight4 = new ModelRenderer(this, 250, 93);
        this.JPDecorationPieceRight4.setRotationPoint(-5.5F, 4.0F, 5.0F);
        this.JPDecorationPieceRight4.addBox(0.0F, 0.0F, 0.0F, 1, 4, 2, 0.0F);
        this.JPDecorationPieceLeft13 = new ModelRenderer(this, 238, 102);
        this.JPDecorationPieceLeft13.setRotationPoint(0.0F, 10.0F, 4.0F);
        this.JPDecorationPieceLeft13.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.JPDecorationPieceLeft6 = new ModelRenderer(this, 252, 118);
        this.JPDecorationPieceLeft6.setRotationPoint(0.5F, 9.5F, 7.5F);
        this.JPDecorationPieceLeft6.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.JPDecorationPieceRight14 = new ModelRenderer(this, 238, 102);
        this.JPDecorationPieceRight14.setRotationPoint(-6.0F, 10.0F, 4.0F);
        this.JPDecorationPieceRight14.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.JPDecorationPieceLeft4 = new ModelRenderer(this, 250, 93);
        this.JPDecorationPieceLeft4.setRotationPoint(4.5F, 4.0F, 5.0F);
        this.JPDecorationPieceLeft4.addBox(0.0F, 0.0F, 0.0F, 1, 4, 2, 0.0F);
        this.JPDecorationPieceLeft15 = new ModelRenderer(this, 238, 96);
        this.JPDecorationPieceLeft15.setRotationPoint(2.0F, 3.5F, 3.0F);
        this.JPDecorationPieceLeft15.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
        this.JPDecorationPieceLeft3 = new ModelRenderer(this, 248, 88);
        this.JPDecorationPieceLeft3.setRotationPoint(1.5F, 4.0F, 7.5F);
        this.JPDecorationPieceLeft3.addBox(0.0F, 0.0F, 0.0F, 3, 4, 1, 0.0F);
        this.JPDecorationPieceLeft7 = new ModelRenderer(this, 238, 107);
        this.JPDecorationPieceLeft7.setRotationPoint(1.0F, 9.0F, 3.5F);
        this.JPDecorationPieceLeft7.addBox(0.0F, 0.0F, 0.0F, 4, 1, 5, 0.0F);
        this.JPDecorationPieceRight3 = new ModelRenderer(this, 248, 88);
        this.JPDecorationPieceRight3.setRotationPoint(-4.5F, 4.0F, 7.5F);
        this.JPDecorationPieceRight3.addBox(0.0F, 0.0F, 0.0F, 3, 4, 1, 0.0F);
        this.JPDecorationPieceLeft2 = new ModelRenderer(this, 252, 86);
        this.JPDecorationPieceLeft2.setRotationPoint(2.5F, 1.5F, 5.5F);
        this.JPDecorationPieceLeft2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.JPDecorationPieceRight12 = new ModelRenderer(this, 246, 122);
        this.JPDecorationPieceRight12.setRotationPoint(-5.0F, 10.0F, 3.0F);
        this.JPDecorationPieceRight12.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);

        this.bipedBody.cubeList.clear();
        this.bipedHead.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();

        this.bipedBody.addChild(this.JPDecorationPieceRight8);
        this.bipedBody.addChild(this.JPDecorationPieceLeft14);
        this.bipedBody.addChild(this.JPDecorationPieceLeft11);
        this.bipedBody.addChild(this.JPDecorationPieceLeft1);
        this.bipedBody.addChild(this.JPDecorationPieceLeft8);
        this.bipedBody.addChild(this.JPDecorationPieceRight7);
        this.bipedBody.addChild(this.JPDecorationPieceRight6);
        this.bipedBody.addChild(this.JPDecorationPieceLeft5);
        this.bipedBody.addChild(this.JPDecorationPieceRight10);
        this.bipedBody.addChild(this.JPDecorationPieceRight15);
        this.bipedBody.addChild(this.JPDecorationPieceRight1);
        this.bipedBody.addChild(this.JPDecorationPieceRight9);
        this.bipedBody.addChild(this.JPDecorationPieceRight11);
        this.bipedBody.addChild(this.JPDecorationPieceRight5);
        this.bipedBody.addChild(this.JetPackMainPieceLeft);
        this.bipedBody.addChild(this.JPDecorationPieceRight13);
        this.bipedBody.addChild(this.JPDecorationPieceLeft10);
        this.bipedBody.addChild(this.JPDecorationPieceLeft12);
        this.bipedBody.addChild(this.JPDecorationPieceLeft9);
        this.bipedBody.addChild(this.JPDecorationPieceRight2);
        this.bipedBody.addChild(this.JPDecorationPiece2);
        this.bipedBody.addChild(this.JPDecorationPiece1);
        this.bipedBody.addChild(this.JetPackMainPieceRight);
        this.bipedBody.addChild(this.JPDecorationPieceRight4);
        this.bipedBody.addChild(this.JPDecorationPieceLeft13);
        this.bipedBody.addChild(this.JPDecorationPieceLeft6);
        this.bipedBody.addChild(this.JPDecorationPieceRight14);
        this.bipedBody.addChild(this.JPDecorationPieceLeft4);
        this.bipedBody.addChild(this.JPDecorationPieceLeft15);
        this.bipedBody.addChild(this.JPDecorationPieceLeft3);
        this.bipedBody.addChild(this.JPDecorationPieceLeft7);
        this.bipedBody.addChild(this.JPDecorationPieceRight3);
        this.bipedBody.addChild(this.JPDecorationPieceLeft2);
        this.bipedBody.addChild(this.JPDecorationPieceRight12);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.bipedRightArm.render(f5);
        this.bipedHead.render(f5);
        this.bipedBody.render(f5);
        this.bipedLeftArm.render(f5);
        this.bipedRightLeg.render(f5);
        this.bipedLeftLeg.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
