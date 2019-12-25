grammar drm;

QUERY: 'query';
INSERT: 'insert';

APP: 'app';
LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
LBRACK : '[';
RBRACK : ']';
EQUALS: '=';
COMMA : ',';

IdentifierStart: [a-zA-Z_];
IdentifierPart: [a-zA-Z0-9$_];
Identifier: IdentifierStart IdentifierPart*;
Value: IdentifierStart IdentifierPart*;
AssignStatement: Identifier '=' Value;

create: (APP init_block) exec_bock?;

init_block:
    '('
        AssignStatement? (',' AssignStatement)*
    ')'
;

exec_bock:
    '{'
        AssignStatement? (',' AssignStatement)*
    '}'
;

SPACES: [ \t\r\n]+ -> channel(HIDDEN);
