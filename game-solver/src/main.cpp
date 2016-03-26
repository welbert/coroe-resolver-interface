
#include "SearchSolver.hpp"
#include "A.hpp"
#include "BFS.hpp"
#include "DFS.hpp"
#include "Parameters.hpp"
#include <fstream>
#include <iostream>

using namespace std;

int get_solution_size(const vector<string> & s, const vector<int> & sol){
  int res = 0;
  for(int x: sol) res += s[x].size();
  return res;
}

/**
  Função responsável por executar testes e gerar estatísticas.
*/
void test(){
  srand(42);
  const int COUNT = 5;
  const char chars[] = {'a', 'd', 'e', 'm'}; // ademp
  const int TESTCOUNT = 1000;
  const int MAXSEQ = 2500;
  const int MAXCHAR = 750;

  int sv_BFS = 0, sv_A = 0, sv_DFS = 0;
  int sz_BFS = 0, sz_A = 0, sz_DFS = 0;
  int SUCCESS = 0;

  for(int i = 0; i < TESTCOUNT; i++){
    if(i && i % 50 == 0)
      cout << i << " tests processed..." << endl;
    int sa = rand() % MAXSEQ + 1;
    int sb = rand() % MAXSEQ + 1;
    vector<string> a, b;

    for(int j = 0; j < sa; j++){
      int sz = rand() % MAXCHAR + 2;
      string res;
      for(int k = 0; k < sz; k++){
        res += chars[rand()%COUNT];
      }

      // cout << res << endl;
      a.push_back(res);
    }

    for(int j = 0; j < sb; j++){
      int sz = rand() % MAXCHAR + 4;
      string res;
      for(int k = 0; k < sz; k++){
        res += chars[rand()%COUNT];
      }

      //cout << res << endl;
      b.push_back(res);
    }

    BFS solver1(a,b);
    pair<vector<int>, vector<int>> sol1 = solver1.solve();
    sv_BFS += solver1.states;
    sz_BFS += get_solution_size(a, sol1.first);

    A solver2(a,b);
    pair<vector<int>, vector<int>> sol2 = solver2.solve();
    sv_A += solver2.states;
    sz_A += get_solution_size(a, sol2.first);

    /*DFS solver3(a,b);
    pair<vector<int>, vector<int>> sol3 = solver3.solve();
    sv_DFS += solver3.states;
    sz_DFS += get_solution_size(a, sol3.first);*/

    SUCCESS += sol1.first.size() > 0;
  }

  cout << endl;
  cout << "Avg number of visited states / Avg size of the solution" << endl;
  cout << "BFS: " << endl;
  cout << 1.0*sv_BFS/SUCCESS << " " << 1.0*sz_BFS/SUCCESS << endl << endl;
  cout << "A*: " << endl;
  cout << 1.0*sv_A/SUCCESS << " " << 1.0*sz_A/SUCCESS << endl << endl;

  cout << "SUCCESS: " << SUCCESS << endl;
  cout << "FAILS: " << TESTCOUNT-SUCCESS << endl;
}

int main(int argc, char* argv[]) {

  // se não houverem argumentos, gera testes aleatórios e executa
  if(argc == 1){
    test();
    return 0;
  }

  Parameters parameters(argc, argv);

  vector<string> a, b;

  ifstream in(parameters.get_input_path().c_str());
  if (!in.is_open()) {
    cerr << "o arquivo de entrada nao pode ser lido" << endl;
    throw new runtime_error("");
  }

  int a_sz, b_sz;
  in >> a_sz;
  for (int i = 0; i < a_sz; i++) {
    a.emplace_back();
    in >> a.back();
  }

  in >> b_sz;
  for (int i = 0; i < b_sz; i++) {
    b.emplace_back();
    in >> b.back();
  }

  in.close();
  vector<int> resa, resb;
  SearchSolver* solver;

  if (parameters.is_informed())
    solver = new A(a, b);
  else
    solver = new BFS(a, b);

  tie(resa, resb) = solver->solve();

  ofstream out(parameters.get_output_path().c_str());
  if (!out.is_open()) {
    cerr << "o arquivo de saida nao pode ser escrito" << endl;
    throw new runtime_error("");
  }

  if (resa.empty() || resb.empty()) {
    cerr << "a busca terminou mas nenhuma solucao foi encontrada" << endl;
    out << 0 << endl;
    out.close();
    return 0;
  }

  out << 1 << endl;
  out << resa.size() << endl;
  for (unsigned i = 0; i < resa.size(); i++) {
    out << resa[i] << " \n"[i + 1 == resa.size()];
  }

  out << resb.size() << endl;
  for (unsigned i = 0; i < resb.size(); i++) {
    out << resb[i] << " \n"[i + 1 == resb.size()];
  }

  // imprime ultimas linhas somente para debug
  out << endl;
  for (unsigned i = 0; i < resa.size(); i++) {
    out << a[resa[i]];
  }
  out << endl;

  out.close();
}
