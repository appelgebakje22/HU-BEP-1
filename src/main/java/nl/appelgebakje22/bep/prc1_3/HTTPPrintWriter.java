package nl.appelgebakje22.bep.prc1_3;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Objects;

public final class HTTPPrintWriter extends PrintWriter {

	HTTPPrintWriter(OutputStream out) {
		super(Objects.requireNonNull(out));
	}

	@Override
	public void println() {
		this.print("\r\n");
		this.flush();
	}
}