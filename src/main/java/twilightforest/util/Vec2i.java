package twilightforest.util;

import java.util.Objects;

public final class Vec2i {
	public int x, z;

	public Vec2i() {
	}

	public Vec2i(int x, int z) {
		this.x = x;
		this.z = z;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o instanceof Vec2i vec2i) {
			return vec2i.x == this.x && vec2i.z == this.z;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, z);
	}
}
