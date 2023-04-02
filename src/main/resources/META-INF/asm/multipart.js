// noinspection ES6ConvertVarToLetConst

var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');

var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode')
var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode')

// noinspection JSUnusedGlobalSymbols
function initializeCoreMod() {
    return {
        'hitbox': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.world.level.Level',
                'methodName': ASM.mapMethod('m_6249_'),
                'methodDesc': '(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insertBefore(
                    ASM.findFirstInstruction(methodNode, Opcodes.ARETURN),
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 0),
                        new VarInsnNode(Opcodes.ALOAD, 1),
                        new VarInsnNode(Opcodes.ALOAD, 2),
                        new VarInsnNode(Opcodes.ALOAD, 3),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'twilightforest/ASMHooks',
                            'multipartHitbox',
                            '(Ljava/util/List;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        },
        'id': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.server.level.ServerLevel',
                'methodName': ASM.mapMethod('m_143317_'),
                'methodDesc': '(I)Lnet/minecraft/world/entity/Entity;'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insertBefore(
                    ASM.findFirstInstruction(methodNode, Opcodes.ARETURN),
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ILOAD, 1),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'twilightforest/ASMHooks',
                            'multipartFromID',
                            '(Lnet/minecraft/world/entity/Entity;I)Lnet/minecraft/world/entity/Entity;',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        },
        'trackingstart': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.server.level.ServerLevel$EntityCallbacks',
                'methodName': ASM.mapMethod('m_141985_'),
                'methodDesc': '(Lnet/minecraft/world/entity/Entity;)V'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insert(
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 1),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'twilightforest/ASMHooks',
                            'trackingStart',
                            '(Lnet/minecraft/world/entity/Entity;)V',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        },
        'trackingend': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.server.level.ServerLevel$EntityCallbacks',
                'methodName': ASM.mapMethod('m_141981_'),
                'methodDesc': '(Lnet/minecraft/world/entity/Entity;)V'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insert(
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 1),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'twilightforest/ASMHooks',
                            'trackingEnd',
                            '(Lnet/minecraft/world/entity/Entity;)V',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        },
        'sync': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.server.level.ServerEntity',
                'methodName': ASM.mapMethod('m_8543_'),
                'methodDesc': '()V'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insert(
                    ASM.findFirstInstruction(methodNode, Opcodes.GETFIELD),
                    ASM.listOf(
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'twilightforest/ASMHooks',
                            'updateMultiparts',
                            '(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/entity/Entity;',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        },
        'bake': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.entity.EntityRenderDispatcher',
                'methodName': ASM.mapMethod('m_6213_'),
                'methodDesc': '(Lnet/minecraft/server/packs/resources/ResourceManager;)V'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insert(
                    ASM.findFirstInstruction(methodNode, Opcodes.INVOKESPECIAL),
                    ASM.listOf(
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'twilightforest/ASMHooks',
                            'bakeMultipartRenders',
                            '(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        },
        'renderer': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.entity.EntityRenderDispatcher',
                'methodName': ASM.mapMethod('m_114382_'),
                'methodDesc': '(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                var lastInstruction = null;
                for (var index = instructions.size() - 1; index > 0; index--) {
                    var /*org.objectweb.asm.tree.AbstractInsnNode*/ node = instructions.get(index);
                    if (lastInstruction == null &&

                        node instanceof InsnNode &&

                        node.getOpcode() === Opcodes.ARETURN

                    )
                        lastInstruction = node;

                }
                instructions.insertBefore(
                    lastInstruction,
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 1),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'twilightforest/ASMHooks',
                            'getMultipartRenderer',
                            '(Lnet/minecraft/client/renderer/entity/EntityRenderer;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        },
        'render': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.LevelRenderer',
                'methodName': ASM.mapMethod('m_109599_'),
                'methodDesc': '(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lcom/mojang/math/Matrix4f;)V'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                var lastInstruction = null;
                for (var index = instructions.size() - 1; index > 0; index--) {
                    var /*org.objectweb.asm.tree.MethodInsnNode*/ node = instructions.get(index);
                    if (lastInstruction == null &&

                        node instanceof MethodInsnNode &&

                        node.getOpcode() === Opcodes.INVOKEVIRTUAL &&

                        equate(node.owner, 'net/minecraft/client/multiplayer/ClientLevel') &&

                        equate(node.name, ASM.mapMethod('m_104735_')) &&

                        equate(node.desc, '()Ljava/lang/Iterable;')

                    )
                        lastInstruction = node;

                }
                instructions.insert(
                    lastInstruction,
                    ASM.listOf(
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'twilightforest/ASMHooks',
                            'renderMutiparts',
                            '(Ljava/lang/Iterable;)Ljava/lang/Iterable;',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        }
    }
}

function equate(/*java.lang.Object*/ a, b) {
    return a.equals(b);
}
