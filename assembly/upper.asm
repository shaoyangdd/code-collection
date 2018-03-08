;将Basic字符串转成大写,HeLLo转成小写
assume cs:codesg,ds:datasg

  ;数据段，字符串
  datasg segment
    db 'Basic'
    db 'HeLLo'
  datasg ends

  ;代码段
  codesg segment
    start:

      ;初始化数据段地址
      mov ax,datasg
      mov ds,ax
      mov ax,0h

      ;循环处理
      mov cx,5h;循环次数为Basic字符数=HeLLo字符数
      ;小转大处理
    s:mov al,[bx];
      and al,11011111b
      mov [bx],al
      ;大转小处理
      mov al,[bx+5]
      or al,00100000b
      mov [bx+5],al
      inc bx
      loop s

      ;返回
      mov ax,4c00h
      int 21h
  codesg ends

end start
