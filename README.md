# T2_3-Formais
Repositório para o trabalho 2 e 3 de Linguagens Formais - 18.2


Gramática livre da pseudolinguagem para qual será feito o analisador léxico e sintático.

< program > ::= < block >
< block > ::= {< decls >< stmts >}
< decls > ::= < decl >< decls > | ε
< decl > ::= < type > id ;
< type > ::= basic < types >
< types > ::= [ num ] < types > | ε
< stmts > ::= < stmt >< stmts > | ε
< stmt > ::= < loc >=< bool > ;
| < matched_if >
| < open_if >
| while ( < bool > )stmt
| do < stmt > while ( < bool > );
| break ;
| < block >
< matched_if > ::= if (< bool >)then < matched_if > else < matched_if >
< open_if > ::= if (< bool >)then < stmt >
| if (< bool >)then < mached_if > else < open_if >
< loc > ::= id < locs >
< locs > ::= [ < bool > ] < locs > | ε
< bool > ::= < join > | < join > || < bool >
< join > ::= < equality > | < equality > || < join >
< equality > ::= < rel > | < rel > == < equality > | < rel > ! = < equality >
< rel > ::= < expr > < < expr > | < expr > <= < expr >
| < expr > >= < expr > | < expr > > < expr > | < expr >
< expr > ::= < term >< exprs >
< exprs > ::= + < term >< exprs > | − < term >< exprs > | ε
< term > ::= < unary >< terms >
< terms > ::= ∗ < unary >< terms > | / < unary >< terms > | ε
< unary > ::= ! < unary > | − < unary > | < f actor >
< factor > ::= (bool) | < loc > | num | real | true | false

Tipos de tokens:

| Token_Name | Description | Example | E.R. |
| ---------- | ----------- | ------- | ---- |
| openBlock | opens a block | { | nd |
| closeBlock | opens a block | } | nd |
| endOfLine | terminates a line| ; | nd |
| id | identifies a variable | obraId | nd |
| type | identifies a predfined type | int | nd |

