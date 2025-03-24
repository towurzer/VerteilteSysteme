package homework.assignment_02.philipp.ex1.server;

import java.util.Objects;
import java.util.UUID;

public class Player {

    private final UUID id;

    public Player() {
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
