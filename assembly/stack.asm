;将一段空间的数据放到另一段栈空间中
assume cs:codesg,ds:datasg,ss:stacksg

  ;要放到栈空间的数据
  datasg segment
    dw 0102h,0304h,0506h
  datasg ends

  ;目标栈空间
  stacksg segment
    dw 0h,0h,0h
  stacksg ends

  codesg segment
   start:
   
    ;初始化数据段段地址
    mov ax,datasg
    mov ds,ax
    
    ;初始化栈段
    mov ax,stacksg
    mov ss,ax
    
    ;入栈操作
    mov bx,0h;初始化数据段偏移地址
    mov cx,3h;初始化循环次数，一次push是一个字单元，而不是byte，所以循环次数为3
    mov sp,6h;初始化栈的偏移地址，数据段占0，1，2，栈段占3，4，5，sp在6
  s:push [bx];数据段中的数据压到栈
    add bx,2h;数据段偏移地址+2
    loop s
    
    ;返回
    mov ax,4c00h
    int 21h
    
  codesg ends
end start
