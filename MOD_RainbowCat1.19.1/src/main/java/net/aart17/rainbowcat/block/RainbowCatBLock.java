package net.aart17.rainbowcat.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


public class RainbowCatBLock extends Block {
    public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 2);
/*
    0 - Off
    1 - On warm light
    2 - On RGB iridescent light
*/
    public  static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final int MAX = RotationSegment.getMaxSegmentIndex();
    private static final int ROTATIONS = MAX + 1;
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
    public RainbowCatBLock() {
        super(Properties.of(Material.DECORATION)
                .noOcclusion()
                .instabreak()
                .lightLevel(state -> state.getValue(RainbowCatBLock.LIT) ? 15 : 0)
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0));
        this.registerDefaultState(this.defaultBlockState().setValue(STATE, 0));
        this.registerDefaultState(this.defaultBlockState().setValue(LIT,  false));
    }
// Change shape
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }
    @Override
    public boolean hasDynamicShape(){
        return true;
    }
// Change light
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level world, @NotNull BlockPos pose,
                                          @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!world.isClientSide) {
            BlockState newState = state;
            newState = newState.setValue(LIT, 2 != state.getValue(STATE));
            newState = newState.cycle(STATE);
            world.setBlock(pose, newState, 3);
        }
        return InteractionResult.SUCCESS;
    }
// Rotation changes
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(ROTATION, RotationSegment.convertToSegment(context.getRotation()));
    }
    @Override
    public @NotNull BlockState rotate(@NotNull BlockState state, @NotNull Rotation rotation) {
        return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), ROTATIONS));
    }
    @Override
    public @NotNull BlockState mirror(@NotNull BlockState state, @NotNull Mirror mirror) {
        return state.setValue(ROTATION, mirror.mirror(state.getValue(ROTATION), ROTATIONS));
    }
// State for placement
    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader reader, BlockPos pos) {
        return canSupportCenter(reader, pos.below(), Direction.UP);
    }
    public @NotNull BlockState updateShape(@NotNull BlockState blockState, @NotNull Direction direction,
                                           @NotNull BlockState state, @NotNull LevelAccessor accessor,
                                           @NotNull BlockPos pos1, @NotNull BlockPos pos) {
        return direction == Direction.DOWN && !this.canSurvive(blockState, accessor, pos1) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, state, accessor, pos1, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        builder.add(ROTATION, LIT, STATE);
    }
}