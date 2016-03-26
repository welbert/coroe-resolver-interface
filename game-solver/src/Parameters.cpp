#include "Parameters.hpp"
#include <iostream>

Parameters::Parameters(int argc, char* argv[]) {
  if (argc < 3)
    throw new invalid_argument("numero de argumentos incorreto");
  in = string(argv[1]);
  out = string(argv[2]);
  informed = argc > 3 && string(argv[3]) == "--informed";
}
