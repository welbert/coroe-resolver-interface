#include "SearchSolver.hpp"
#include <map>
#include <string>
#include <vector>

using namespace std;

/**
  Classe responsável por resolver o jogo da coreografia usando A*.
  Herda funções de SearchSolver.
*/
class A : public SearchSolver {
  private:


  /**
    Armazena o pai de cada estado explorado na árvore de busca.
  */
  map<State, pair<State, int> > parent;
  vector<int> resa, resb;

  /**
    Armazena o custo de cada estado explorado.
  */
  map<State, int> dist;

  public:
  A(const vector<string>& a, const vector<string>& b) : SearchSolver(a, b) { states = 0; }

  int states;
  /**
    Resolve o problema usando A*.
    @return um par de vetores de inteiros: as sequências resultantes de cada
      um dos dançarinos.
  */
  pair<vector<int>, vector<int> > solve();
};
