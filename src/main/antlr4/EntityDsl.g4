grammar EntityDsl;

@header {
package io.activise.entitydsl.antlr;
}

compilationUnit: entity*;

entity
  : KW_ENTITY ID tableName? entityBody
  ;
tableName: RP ID LP;
entityBody
  : RCB member* LCB
  ;

member
  : attribute
  ;

attribute
  : ID associationType (type | arrayType) (RCB attributeOption (COMMA attributeOption)* LCB)? SEMICOLON
  ;
associationType: NORMAL | BI | EMBEDDED;
type: ID;
arrayType: ID RB LB;
attributeOption: ID;

KW_ENTITY: 'entity';
RP: '(';
LP: ')';
RCB: '{';
LCB: '}';
RB: '[';
LB: ']';
COMMA: ',';
SEMICOLON: ';';
NORMAL: ':';
BI: ':+';
EMBEDDED: ':-';
ID: [a-zA-Z0-9_]+;
WS: [ \t\r\n]+ -> skip;
