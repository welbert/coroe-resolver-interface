#include "A.hpp"
#include <algorithm>
#include <iostream>
#include <queue>
#include <stdexcept>

using namespace std;

pair<vector<int>, vector<int> > A::solve() {
  // inicializa o conjunto de estados vistos
  states = 1;
  parent.clear();
  dist.clear();

  // enfileira o estado inicial, mantendo desta vez uma fila de prioridades.
  // a prioridade de cada estado é f(n) = g(n) + h(n)
  State initial = {"", 0, 0};
  priority_queue<pair<int, State>, vector<pair<int, State> >, greater<pair<int, State> > > PQ;
  parent[initial] = make_pair(initial, -1);
  dist[initial] = 0;
  PQ.push({0, initial});

  // continua enquanto houver estados a serem visitados OU
  // o estado final ainda não tiver sido encontrado
  while (!PQ.empty()) {
    // retira o primeiro estado da fila
    State cur;
    int func; // valor de f(cur)
    int cost; // valor de g(cur)
    tie(func, cur) = PQ.top();
    cost = dist[cur];
    PQ.pop();

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

      // checa quantos caracteres existem em comum entre a string adicionada
      // e a cauda atual
      for (; matches < s_sz && matches < suffix_sz; matches++)
        if (s[matches] != cur.suffix[matches])
          break;

      // caso a sequência atual não possa ser estendida por esta string,
      // continua para o próximo vizinho (caso de transição inválida)
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

      // computa o valor da função de custo e da função de avaliaçao do
      // estado vizinho
      int FC = cost + matches;
      int FA = next.suffix.size();

      // checa se o vizinho já foi visitado
      if (parent.find(next) == parent.end()) {
        // caso não tenha sido, enfileira o mesmo com prioridade igual a
        // f(next) = g(next) + h(next), armazenando o pai na árvore de busca e
        //  a transição (sequência de movimentos) que foi utilizada.
        // além disso, armazena o custo para alcançar tal estado.
        parent[next] = {cur, i};
        dist[next] = FC;
        PQ.push({FC + FA, next});
        states++;
      }
    }
  }

  // nenhuma solução foi encontrada
  return {{}, {}};
}
