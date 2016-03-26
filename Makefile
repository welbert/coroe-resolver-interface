make: clean
	cd game-solver && $(MAKE) && cp solver ..

clean:
	rm -rf solver
	cd game-solver && $(MAKE) clean
