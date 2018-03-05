;求和，内存单元0:200处到0:204几个内存单元中数值的和
assume cs:codesg
    codesg segment
      
      ;初始化ds
      mov bx,0h
      mov ds,bx
      mov bx,200h
      ;初始化cx计数器
      mov cx,5h
      ;计算求和
      mov dx,0h
    s:mov al,[bx]
      mov ah,0;必须高低位分别赋值，否则内存单元中的数就赋到了高位
      inc bx
      add dx,ax
      loop s
      
      mov ax,4c00h
      int 21h
    codesg ends
    
  end