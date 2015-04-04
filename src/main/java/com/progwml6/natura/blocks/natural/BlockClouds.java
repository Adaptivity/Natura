package com.progwml6.natura.blocks.natural;

import java.util.List;

import mantle.blocks.util.BlockVariant;
import mantle.blocks.util.IBlockWithVariants;
import mantle.blocks.util.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.progwml6.natura.Natura;
import com.progwml6.natura.blocks.BlocksNatura;
import com.progwml6.natura.creativetabs.NaturaCreativeTabs;

public class BlockClouds extends Block implements IBlockWithVariants
{
	public static final BlockVariant
			WHITE = new BlockVariant(0, "cloud_white"),
			GREY = new BlockVariant(1, "cloud_gray"),
			DARK = new BlockVariant(2, "cloud_dark"),
			SULFER = new BlockVariant(3, "cloud_sulfur");

	public static final PropertyVariant CLOUD_TYPE = PropertyVariant.create("variant", WHITE, GREY, DARK, SULFER);

	public BlockClouds()
	{
		super(BlocksNatura.cloud);
		this.setStepSound(soundTypeCloth);
		this.setHardness(0.3F);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(CLOUD_TYPE, WHITE));
		this.setCreativeTab(NaturaCreativeTabs.tab);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : CLOUD_TYPE.getAllowedValues())
		{
			list.add(new ItemStack(itemIn, 1, variant.getMeta()));
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		int meta = this.getMetaFromState(state);
		if (meta == 3 && entityIn instanceof EntityArrow && !worldIn.isRemote)
		{
			EntityArrow entityarrow = (EntityArrow) entityIn;

			if (entityarrow.isBurning())
			{
				this.explode(worldIn, pos, 1, entityarrow.shootingEntity instanceof EntityLiving ? (EntityLiving) entityarrow.shootingEntity : null);
				worldIn.setBlockToAir(pos);
				return;
			}
		}

		if (entityIn.motionY < 0.0D)
		{
			entityIn.motionY *= 0.005D;
		}
		entityIn.fallDistance = 0.0F;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		int meta = this.getMetaFromState(state);
		if (meta == 3 && playerIn.getCurrentEquippedItem() != null && playerIn.getCurrentEquippedItem().getItem() == Items.flint_and_steel)
		{
			worldIn.setBlockToAir(pos);
			this.explode(worldIn, pos, 1, playerIn);
			return true;
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitX);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
	{
	}

	public void explode(World world, BlockPos pos, int size, EntityLivingBase living)
	{
		world.createExplosion(living, pos.getX(), pos.getY(), pos.getZ(), size, true);
	}

	@Override
	public boolean canDropFromExplosion(Explosion par1Explosion)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		Block block = worldIn.getBlockState(pos).getBlock();
		return block != Natura.getBlocks().clouds && super.shouldSideBeRendered(worldIn, pos, side);
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		Block block = worldIn.getBlockState(pos).getBlock();
		return block != Natura.getBlocks().clouds && super.isBlockSolid(worldIn, pos, side);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		if (worldIn.getBlockState(pos.down()).getBlock() == Natura.getBlocks().clouds)
		{
			return null;
		}
		else
		{
			return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 0.0625D, pos.getZ() + 1.0D);
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(CLOUD_TYPE, CLOUD_TYPE.getVariantFromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockVariant) state.getValue(CLOUD_TYPE)).getMeta();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { CLOUD_TYPE });
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((BlockVariant) state.getValue(CLOUD_TYPE)).getMeta();
	}

	@Override
	public String getVariantNameFromStack(ItemStack stack)
	{
		return CLOUD_TYPE.getVariantFromMeta(stack.getMetadata()).getName();
	}

}
