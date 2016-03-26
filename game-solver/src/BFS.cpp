#include "BFS.hpp"
#include <algorithm>
#include <iostream>
#include <queue>
#include <stdexcept>

using namespace std;

pair<vector<int>, vector<int> > BFS::solve() {
  // inicializa o conjunto de estados vistos
  states = 1;
  parent.clear();

  // enfileira o estado inicial
  State initial = {"", 0, 0};
  queue<State> Q;
  parent[initial] = make_pair(initial, -1);
  Q.push(initial);

  // continua enquanto houver estados a serem visitados OU
  // o estado final ainda não tiver sido encontrado
  while (!Q.empty()) {

    // retira o primeiro estado da fila
    State cur = Q.front();
    Q.pop();

    // checa se é um estado final
    if (cur.is_final()) {
      // constrói o caminho a partir do map parent e retorna as sequências
      // resultantes
      vector<int> res[2];

      // enquanto o estado atual não for o estado inicial, sobe um nível na
      // árvore e atualiza as sequências resultantes
      while (!cur.is_initial()) {
        State prev;
        int step;
        tie(prev, step) = parent[cur];
        res[prev.current ^ 1].push_back(step);
        cur = prev;
      }

      reverse(res[0].begin(), res[0].end());
      reverse(res[1].begin(), res[1].end());

      // retorna uma solução
      return {res[0], res[1]};
    }

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
      if (parent.find(next) == parent.end()) {
        // caso não tenha sido, enfileira o mesmo, armazenando o pai na árvore
        // de busca e a transição (sequência de movimentos) que foi utilizada
        parent[next] = {cur, i};
        states++;
        Q.push(next);
      }
    }
  }

  // nenhuma solução foi encontrada
  return {{}, {}};
}
