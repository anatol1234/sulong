/*
 * Copyright (c) 2018, Oracle and/or its affiliates.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.oracle.truffle.llvm.parser;

import com.oracle.truffle.llvm.runtime.LLVMContext;
import com.oracle.truffle.llvm.runtime.memory.LLVMStack.UniquesRegion;
import com.oracle.truffle.llvm.runtime.nodes.api.LLVMExpressionNode;
import com.oracle.truffle.llvm.runtime.types.Type;

public interface AllocFactory {

    LLVMExpressionNode createAlloc(NodeFactory nodeFactory, LLVMContext context, Type type);

    public static class AllocaFactory implements AllocFactory {
        private AllocaFactory() {
        }

        @Override
        public LLVMExpressionNode createAlloc(NodeFactory nodeFactory, LLVMContext context, Type type) {
            return nodeFactory.createAlloca(context, type);
        }
    }

    public static class UniqueAllocFactory implements AllocFactory {
        private final UniquesRegion uniquesRegion;

        private UniqueAllocFactory(UniquesRegion uniquesRegion) {
            this.uniquesRegion = uniquesRegion;
        }

        @Override
        public LLVMExpressionNode createAlloc(NodeFactory nodeFactory, LLVMContext context, Type type) {
            return nodeFactory.createUniqueAlloc(context, type, uniquesRegion);
        }
    }

    public static AllocFactory createAllocaFactory() {
        return new AllocaFactory();
    }

    public static AllocFactory createUniqueAllocFactory(UniquesRegion uniquesRegion) {
        return new UniqueAllocFactory(uniquesRegion);
    }

}
