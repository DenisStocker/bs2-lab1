public enum Code {
    CONST((byte) 0x01),
    ADD((byte) 0x11),
    SUB((byte) 0x12),
    MUL((byte) 0x13),
    PRINT((byte) 0x20);

    public final Byte instruction;

    Code(Byte instruction) {
        this.instruction = instruction;
    }
}
