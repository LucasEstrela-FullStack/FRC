# 🤖 FRC — PathPlanner & Robot Autonomous

Repositório dedicado a **anotações, estudos e exemplos práticos de programação para FIRST Robotics Competition (FRC)**, com foco em **PathPlanner, WPILib e sistemas de navegação/autonomia de robôs**.

O projeto reúne conceitos utilizados no desenvolvimento de rotinas autônomas, planejamento de trajetórias, controle do chassi e integração com sensores.

---

## 🎯 Objetivo

Durante minha experiência com robótica, trabalhei com o desenvolvimento de rotinas voltadas à **autonomia e movimentação precisa do robô**, utilizando o **PathPlanner integrado ao ecossistema WPILib**.

A proposta é estudar e documentar como criar movimentos:

* Precisos
* Repetíveis
* Suaves
* Otimizados
* Orientados por dados de sensores

Esses recursos são especialmente importantes durante o **período autônomo das competições FRC**, no qual o robô precisa executar ações sem intervenção direta do operador.

---

## ⚙️ Como funciona

O fluxo básico utilizado para criação e execução de trajetórias é:

```text
┌──────────────────────┐
│     PathPlanner      │
│  Criação da trajetória│
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│       WPILib         │
│ Integração e controle│
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│  Sensores / Feedback │
│ Encoders • NavX • IMU│
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│       Robô FRC       │
│ Movimento e autonomia│
└──────────────────────┘
```

### Fluxo de desenvolvimento

1. Criar a trajetória utilizando o **PathPlanner**.
2. Definir pontos, curvas, velocidades e restrições de movimento.
3. Integrar a trajetória ao projeto desenvolvido com **WPILib**.
4. Executar a rotina através do sistema de comandos do robô.
5. Utilizar sensores para obter feedback da posição e orientação.
6. Ajustar parâmetros para melhorar precisão e estabilidade.
7. Validar o comportamento em testes controlados.

---

# 🧩 Tecnologias e Bibliotecas

## WPILib

**WPILib** é o principal framework utilizado no desenvolvimento de software para FRC.

Neste contexto, ele é utilizado para:

* Controle de motores e atuadores;
* Leitura e integração de sensores;
* Arquitetura baseada em comandos;
* Controle autônomo e teleoperado;
* PID e outros mecanismos de controle;
* Odometria e posicionamento;
* Integração com bibliotecas externas.

A integração com PathPlanner permite utilizar trajetórias planejadas dentro das rotinas de controle do robô.

---

## 🗺️ PathPlanner

O **PathPlanner** é utilizado para criação e execução de trajetórias para robôs FRC.

Com ele é possível definir visualmente:

* Waypoints;
* Curvas;
* Velocidade;
* Aceleração;
* Restrições de movimento;
* Rotas autônomas;
* Sequências de trajetórias.

### Principais benefícios

* Planejamento visual;
* Movimentos mais suaves;
* Maior repetibilidade;
* Controle de aceleração e velocidade;
* Facilitação do desenvolvimento autônomo;
* Integração com sistemas de controle e sensores.

O objetivo é transformar uma estratégia de movimentação em uma trajetória que o robô consiga executar de maneira previsível.

---

## ⚡ CTRE Phoenix

A **CTRE Phoenix** fornece ferramentas para integração com controladores e componentes da **Cross The Road Electronics (CTRE)** utilizados em robôs FRC.

Entre suas aplicações estão:

* Controle de motores;
* Configuração de controladores;
* Feedback de sensores;
* Controle em malha fechada;
* Monitoramento de parâmetros;
* Ajustes de desempenho.

A biblioteca complementa o sistema de controle do robô ao fornecer uma interface para os dispositivos de hardware compatíveis.

---

## 🧭 NavX

O **NavX** é utilizado como sistema de medição inercial para obter informações relacionadas à orientação e movimento do robô.

Pode fornecer dados como:

* Yaw;
* Pitch;
* Roll;
* Aceleração;
* Orientação angular.

Essas informações podem ser utilizadas para melhorar o controle de direção, correção de trajetória e estimativa de posição.

---

# 🧠 Controle e Feedback

Uma trajetória planejada não depende apenas do caminho definido previamente.

Durante a execução, o robô pode utilizar **feedback dos sensores** para comparar o movimento esperado com o movimento real.

```text
Trajetória desejada
        │
        ▼
   Controlador
        │
        ▼
      Robô
        │
        ▼
     Sensores
        │
        ▼
     Feedback
        │
        └──────────► Correção
```

Essa abordagem permite reduzir erros causados por fatores como:

* Atrito;
* Variações de superfície;
* Inércia;
* Deslizamento das rodas;
* Diferenças entre motores;
* Erros de posicionamento.

---

# 🚀 Autonomia no FRC

Durante o período autônomo, o robô precisa executar uma sequência de ações sem controle manual direto.

Um fluxo simplificado pode ser:

```text
Início da partida
       │
       ▼
Inicialização
       │
       ▼
Leitura dos sensores
       │
       ▼
Execução da trajetória
       │
       ▼
Correção de movimento
       │
       ▼
Execução das ações
       │
       ▼
Finalização do período autônomo
```

O planejamento adequado das trajetórias é importante para aumentar a **consistência e previsibilidade** das ações realizadas pelo robô.

---

# 🛠️ Boas Práticas

Durante o desenvolvimento e testes das rotinas, algumas práticas são importantes:

### 🔹 Testes progressivos

Começar com velocidades reduzidas antes de executar trajetórias em condições próximas às da competição.

### 🔹 Limites de segurança

Definir limites adequados de:

* Velocidade;
* Aceleração;
* Corrente;
* Temperatura;
* Movimento.

### 🔹 Telemetria

Utilizar dados de telemetria para identificar problemas de:

* Posicionamento;
* Velocidade;
* Orientação;
* Erros de trajetória;
* Resposta dos motores.

### 🔹 Tuning

Ajustar os parâmetros de controle de acordo com o comportamento real do robô.

### 🔹 Repetibilidade

Uma trajetória eficiente não deve funcionar apenas uma vez. O objetivo é obter um comportamento consistente durante diferentes execuções.

---

# 📚 Conteúdo do Repositório

Este repositório pode ser utilizado como material de estudo e referência para:

```text
FRC
├── PathPlanner
│   ├── Trajetórias
│   ├── Waypoints
│   └── Autonomous
│
├── WPILib
│   ├── Commands
│   ├── Control
│   └── Robot Systems
│
├── Sensors
│   ├── Encoders
│   └── NavX
│
└── Motor Control
    └── CTRE Phoenix
```

---

# 🔬 Próximos Passos

Alguns pontos que podem ser adicionados ao projeto:

* [ ] Exemplos completos em Java;
* [ ] Exemplos de integração PathPlanner + WPILib;
* [ ] Trajetórias de exemplo;
* [ ] Exemplos de Autonomous Commands;
* [ ] Documentação de tuning;
* [ ] Exemplos utilizando sensores;
* [ ] Odometria e estimativa de posição;
* [ ] Telemetria e análise de desempenho.

---

# 🏁 Contexto

Este repositório faz parte da minha trajetória de aprendizado em **programação, engenharia de software e robótica**, reunindo conhecimentos aplicados durante minha experiência com **FIRST Robotics Competition**.

Além de programação, a robótica proporcionou experiência prática com **controle, eletrônica, integração de hardware, resolução de problemas e trabalho em equipe**.

> **Build. Test. Fail. Improve. Repeat.**

---

## 📖 Referências

* [WPILib](https://docs.wpilib.org/)
* [PathPlanner](https://pathplanner.dev/)
* [CTRE Phoenix](https://pro.docs.ctr-electronics.com/)
* [NavX](https://pdocs.kauailabs.com/navx-mxp/)
* [FIRST Robotics Competition](https://www.firstinspires.org/robotics/frc)

---

<div align="center">

### 🤖 Engineering meets Robotics

**FRC • Autonomous Systems • Path Planning • Control**

</div>
