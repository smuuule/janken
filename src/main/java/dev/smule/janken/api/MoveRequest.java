package dev.smule.janken.api;

import dev.smule.janken.domain.Move;

public record MoveRequest(String name, Move move) {
}
