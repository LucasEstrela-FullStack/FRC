# FRC — PathPlanner e bibliotecas usadas

Este repositório reúne anotações e exemplos sobre o uso de PathPlanner e outras bibliotecas comuns na FIRST Robotics Competition (FRC). Aqui descrevo o objetivo do código, as ferramentas que utilizamos e como aplicamos na fase autônoma e de controle do robô.

## Objetivo
Durante minha passagem pela robótica, foquei em planejar trajetórias precisas para o robô usando o PathPlanner e integrá-las ao sistema de controle (WPILib). O objetivo é permitir movimentos repetíveis, suaves e otimizados para executar tarefas na fase autônoma da competição.

## O que usamos e como aplicamos
A principal ideia é usar o PathPlanner para desenhar trajetórias em uma interface gráfica, gerar o código de movimento (JSON ou código) e integrar esse código ao projeto do robô (WPILib). Em tempo de execução, a trajetória pode ser ajustada com leituras de sensores (encoders, giroscópio, NavX) para garantir maior precisão.

### Como o fluxo funciona (resumido)
- Projetar a trajetória na interface do PathPlanner.
- Exportar/gerar a trajetória para o projeto (JSON ou código).
- Integrar com comandos/rotinas do WPILib para seguir a trajetória.
- Usar feedback de sensores (encoders, giroscópio/NavX) para correção em tempo real.

## Bibliotecas e conceitos importantes
No FRC, as equipes geralmente programam em Java ou C++. As principais bibliotecas e componentes que usamos incluem:

### WPILib
A WPILib é a biblioteca padrão do FRC. Ela fornece ferramentas para:
- Controle de motores e leitura de sensores;
- Estruturas de comando (Command-based) e gerenciamento do ciclo do robô;
- Controle em malha (PID/PIDF) e utilitários de movimento;
- APIs específicas para modos autônomo e teleoperado.

A WPILib facilita integrar trajetórias geradas pelo PathPlanner e executar comandos que controlam o chassi do robô.

### PathPlanner
O PathPlanner é uma ferramenta e biblioteca que permite desenhar e gerar trajetórias autônomas com uma interface gráfica. Vantagens:
- Geração de trajetórias suaves com aceleração e desaceleração planejadas;
- Otimização de tempo e movimentos para evitar ações bruscas;
- Exportação em formatos compatíveis com WPILib e rotinas de controle do robô.

É especialmente útil para manobras que exigem curvas suaves e mudanças de direção em alta velocidade, mantendo precisão.

### CTRE Phoenix
A CTRE Phoenix é uma biblioteca para controlar controladores de motor e sensores da Cross The Road Electronics (por exemplo, Talon SRX e Victor SPX). Ela fornece:
- Integração de baixo nível com controladores de motor;
- Controle em malha fechada (closed-loop) com sensores integrados;
- Ferramentas para configuração, leitura e tuning dos controladores.

### NavX
O NavX é um sensor de navegação inercial (IMU) que fornece orientação (yaw, pitch, roll) e aceleração. Em FRC é usado para:
- Medir a rotação do robô e permitir correções de trajetória;
- Suportar odometria e fusão de sensores para posicionamento;
- Ajudar no controle e estabilidade do chassi.

## Por que usar o PathPlanner no FRC?
- Precisão: cria trajetórias considerando curvas, aceleração e desaceleração para seguimento preciso;
- Consistência: garante movimentos repetíveis na fase autônoma;
- Otimização de tempo: reduz o tempo necessário para executar tarefas (ex.: pegar/entregar objetos);
- Facilidade: a interface gráfica facilita a criação de trajetórias para iniciantes;
- Integração com sensores: permite correções em tempo real usando encoders e IMUs.

## Boas práticas ao usar trajetórias
- Testar primeiro em baixa velocidade e em ambiente controlado;
- Verificar limites de aceleração e corrente dos motores para evitar danos;
- Utilizar logs e telemetria para validar o seguimento das trajetórias;
- Implementar rotinas de segurança para interromper a trajetória em caso de falha.

## Contribuições e próximos passos
- Adicionar exemplos de código (Java/C++) mostrando a integração PathPlanner + WPILib;
- Incluir arquivos de trajetória de exemplo (JSON) e um tutorial passo a passo;
- Documentar o processo de tuning de PID/Feedforward para o chassi específico.

---

Se quiser, posso:
- Adicionar um exemplo de projeto em Java com WPILib e PathPlanner;
- Incluir um passo a passo de como exportar trajetórias do PathPlanner e integrá-las ao código;
- Traduzir ou expandir alguma seção com mais detalhes técnicos.
