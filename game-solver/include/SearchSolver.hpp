#pragma once
#include <string>
#include <vector>

using namespace std;

/**
  Classe base para resolvedores do jogo da coreografia baseados em
  estratégias de busca.
*/
class SearchSolver {
  private:
  /**
    Armazena o conjunto de sequências de movimentos de cada um dos
    dançarinos.
  */
  vector<string> v[2];

  public:
  /**
    Estrutura responsável por representar um estado na forma:
      (cauda, detentor, não-vazia)
  */
  struct State {
    string suffix; // cauda
    int current;   // dançarino detentor da cauda
    int nonempty;  // se as strings construídas são não-vazias

    /**
      Comparador necessário para utilização da estrutura <map>
    */
    bool operator<(const State& rhs) const {
      if (suffix == rhs.suffix)
        if (current == rhs.current)
          return nonempty < rhs.nonempty;
        else
          return current < rhs.current;
      else
        return suffix < rhs.suffix;
    }

    bool operator==(const State& rhs) const { return !(*this < rhs) && !(rhs < *this); }
    friend ostream& operator<<(ostream&, const State&);

    /**
      @return se o estado é inicial
    */
    bool is_initial() { return suffix == "" && nonempty == 0; }

    /**
      @return se o estado é final
    */
    bool is_final() { return suffix == "" && nonempty > 0; }
  };

  SearchSolver(){};

  /**
    Constrói o resolvedor a partir de dois conjuntos de sequências de movimentos.
    @param a conjunto de sequências de movimentos do primeiro dançarino (id = 0)
    @param b conjunto de sequências de movimentos do segundo dançarino (id = 1)
  */
  SearchSolver(const vector<string>& a, const vector<string>& b) {
    v[0] = a;
    v[1] = b;
  }

  /**
    Obtém o conjunto de sequências de movimentos de um dançarino específico.
    @param dancer identificador do dançarino (0 ou 1)
    @return um conjunto de sequências de movimentos
  */
  const vector<string>& get_sequences(int dancer);

  /**
    Resolve o problema usando uma estratégia de busca.
    @return um par de vetores de inteiros: as sequências resultantes de cada
      um dos dançarinos.
  */
  virtual pair<vector<int>, vector<int> > solve();
};
