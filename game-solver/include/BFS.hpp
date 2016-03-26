#pragma once
#include "SearchSolver.hpp"
#include <map>

using namespace std;

/**
* Classe responsável por resolver o jogo da coreografia usando busca em largura.
* Herda funções de SearchSolver.
*/
class BFS : public SearchSolver {
  private:


  /**
    Armazena o pai de cada estado explorado na árvore de busca.
  */
  map<State, pair<State, int> > parent;

  public:
  BFS(const vector<string>& a, const vector<string>& b) : SearchSolver(a, b) { states = 0; }

  int states;
  /**
    Resolve o problema usando busca em largura.
    @return um par de vetores de inteiros: as sequências resultantes de cada
      um dos dançarinos.
  */
  pair<vector<int>, vector<int> > solve();
};
