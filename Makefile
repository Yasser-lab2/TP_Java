# Makefile for TP1 - Java Project
JAVAC = javac
JAVA  = java
SRC   = src
BIN   = bin
FLAGS = -encoding UTF-8

SOURCES = $(SRC)/main/Morse.java \
          $(SRC)/main2/Somme.java \
          $(SRC)/main3/Livre.java $(SRC)/main3/Bibliothèque.java $(SRC)/main3/Test.java \
          $(SRC)/main4/Exercice1.java \
          $(SRC)/main5/Chambre.java $(SRC)/main5/Fichier_Hotel.java $(SRC)/main5/Hotel.java

# Compile all sources
all: compile

compile:
	$(JAVAC) $(FLAGS) -d $(BIN) $(SOURCES)

# ---- Run targets ----
# Usage: make run-morse ARGS="Hello World"
run-morse: compile
	$(JAVA) -cp $(BIN) main.Morse $(ARGS)

# Usage: make run-somme ARGS="10 20 30"
run-somme: compile
	$(JAVA) -cp $(BIN) main2.Somme $(ARGS)

# Usage: make run-test
run-test: compile
	$(JAVA) -cp $(BIN) main3.Test

# Usage: make run-exercice1 ARGS="3.14159 2"
run-exercice1: compile
	$(JAVA) -cp $(BIN) main4.Exercice1 $(ARGS)

# Usage: make run-hotel
run-hotel: compile
	$(JAVA) -cp $(BIN) main5.Hotel

# Clean compiled files
clean:
	if exist $(BIN) rmdir /S /Q $(BIN)
	mkdir $(BIN)

.PHONY: all compile clean run-morse run-somme run-test run-exercice1 run-hotel
