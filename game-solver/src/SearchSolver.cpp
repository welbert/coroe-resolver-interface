#include "SearchSolver.hpp"
#include <iostream>

pair<vector<int>, vector<int> > SearchSolver::solve() {}

const vector<string>& SearchSolver::get_sequences(int dancer) { return v[dancer]; }

ostream& operator<<(ostream& out, const SearchSolver::State& st) {
  out << "(" << st.suffix << ", " << st.current << ", " << st.nonempty << ")\n";
  return out;
}
