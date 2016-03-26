#include "DFS.hpp"
#include <algorithm>
#include <iostream>
#include <queue>
#include <stdexcept>

using namespace std;

bool DFS::dfs(State cur){
  states++;
  visited.insert(cur);
  if(cur.is_final()) return true;

  // se não for estado final, descobre os vizinhos ainda não visitados
  int addon = cur.current ^ 1;
  const vector<string>& sequences = get_sequences(addon);

  // testa cada possibilidade de sequência de movimentos a ser adicionada
  // à coreografia do dançarino cur.current
  for (unsigned i = 0; i < sequences.size(); i++) {
    const string& s = sequences[i];
    int matches = 0;
    int s_sz = s.size();
    int suffix_sz = cur.suffix.size();

    for (; matches < s_sz && matches < suffix_sz; matches++)
      if (s[matches] != cur.suffix[matches])
        break;

    if (matches < s_sz && matches != suffix_sz)
      continue;

    // computa os parâmetros do vizinho. existem dois casos:
    State next;
    if (matches == suffix_sz) {
      // o dançarino detentor da cauda muda
      next = {s.substr(matches), addon, 1};
    } else {
      // o dançarino detentor da cauda permanece igual
      next = {cur.suffix.substr(matches), cur.current, 1};
    }

    // checa se o vizinho já foi visitado
    if(!visited.count(next)){
      if(dfs(next)){
        res[addon].push_back(i);
        return true;
      }
    }
  }
  return false;
}

pair<vector<int>, vector<int> > DFS::solve(){
  State initial = {"", 0, 0};
  if(!DFS::dfs(initial))
    return {{},{}};
  reverse(res[0].begin(), res[0].end());
  reverse(res[1].begin(), res[1].end());
  return {res[0], res[1]};
}
