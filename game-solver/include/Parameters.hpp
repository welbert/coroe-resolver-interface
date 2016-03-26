#pragma once
#include <stdexcept>
#include <string>
#include <vector>

using namespace std;

/**
  Classe responsável por processar os parâmetros passados pela linha de
  comando.
*/
class Parameters {
  string in, out;
  bool informed;

  public:
  Parameters(int argc, char* argv[]);

  /**
    @return o caminho do arquivo de entrada
  */
  string get_input_path() { return in; }

  /**
    @return o caminho do arquivo de saída
  */
  string get_output_path() { return out; }

  /**
    @return se a busca executada deve ser informada
  */
  bool is_informed() { return informed; }
};
