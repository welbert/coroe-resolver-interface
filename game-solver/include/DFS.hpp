#pragma once
#include "SearchSolver.hpp"
#include <map>
#include <set>

using namespace std;

/**
* Classe responsável por resolver o jogo da coreografia usando busca em prof.
* Herda funções de SearchSolver.
*/
class DFS : public SearchSolver {
  private:


  /**
    Armazena o pai de cada estado explorado na árvore de busca.
  */
  set<State> visited;
  vector<int> res[2];

  public:
  DFS(const vector<string>& a, const vector<string>& b) : SearchSolver(a, b) { states = 0; }
  
  int states;
  /**
    Resolve o problema usando busca em profundidade
    @return um par de vetores de inteiros: as sequências resultantes de cada
      um dos dançarinos.
  */
  pair<vector<int>, vector<int> > solve() override;
  bool dfs(State u);
};
