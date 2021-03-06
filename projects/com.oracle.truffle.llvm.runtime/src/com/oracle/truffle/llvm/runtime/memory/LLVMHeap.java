/*
 * Copyright (c) 2016, Oracle and/or its affiliates.
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
package com.oracle.truffle.llvm.runtime.memory;

import com.oracle.truffle.llvm.runtime.LLVMAddress;

public final class LLVMHeap extends LLVMMemory {

    public static LLVMAddress allocateCString(String string) {
        LLVMAddress baseAddress = LLVMMemory.allocateMemory(string.length() + 1);
        long currentAddress = baseAddress.getVal();
        for (int i = 0; i < string.length(); i++) {
            byte c = (byte) string.charAt(i);
            LLVMMemory.putI8(currentAddress, c);
            currentAddress++;
        }
        LLVMMemory.putI8(currentAddress, (byte) 0);
        return baseAddress;
    }

    // current hack: we cannot directly store the LLVMFunction in the native memory due to GC
    public static final int FUNCTION_PTR_SIZE_BYTE = 8;

    public static void putFunctionPointer(LLVMAddress address, long functionIndex) {
        LLVMMemory.putI64(address, functionIndex);
    }

    public static void putFunctionPointer(long ptr, long functionIndex) {
        LLVMMemory.putI64(ptr, functionIndex);
    }

    public static long getFunctionPointer(LLVMAddress addr) {
        return LLVMMemory.getI64(addr);
    }

}
