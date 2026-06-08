# MinhasTarefas

## Informações do Aluno

| Campo | Informação |
|---|---|
| **Nome** | Isequiel Henrique do Nascimento |
| **Matrícula** | isequielnascimento@gmail.com |
| **Data de Entrega** | 07/06/2026 |

---

## Justificativa da Escolha do Tema

O tema **Lista de Tarefas** foi escolhido por ser um domínio familiar e direto. O apo “MinhasTarefas/MyTasks” é um aplicativo Android simples de gerenciamento de arefas pessoais, desenvolvido com Kotlin e Jetpack Compose. O app permite ao usuário adicionar, visualizar, marcar como concluída e excluir tarefas de maneira intuitiva. Toda a interface é construída de forma declarativa com compose. 
---

## Descrição do Funcionamento

O **MinhasTarefas** é um aplicativo de gerenciamento de tarefas com duas telas principais:

### Tela 1 - Lista de Tarefas

- Exibe todas as tarefas cadastradas em uma lista rolável
- Cada item mostra o título, uma **barra lateral colorida** indicando a prioridade (Alta = vermelho, Média = laranja, Baixa = verde) e, quando definida, a **data de vencimento** com destaque por estado (vencida, vence hoje, futura)
- Botão flutuante (FAB) para adicionar uma nova tarefa
- **Checkbox** em cada item para marcar/desmarcar como concluída diretamente na lista
- **Swipe para a esquerda** para excluir uma tarefa
- **Botão "Filtrar"** na barra superior abre um painel com opções de filtro por status (Todas, Pendentes, Concluídas) e ordenação (Padrão, A–Z, Por Prioridade)

### Tela 2 — Detalhes da Tarefa

- Campo de texto para editar o **título** da tarefa
- **Seletor de prioridade** com três opções em botão segmentado: Alta, Média, Baixa
- **Campo de data de vencimento** que abre um calendário ao ser tocado; botão X para limpar a data
- **Checkbox** para marcar a tarefa como concluída
- **Botão Salvar** que persiste as alterações e retorna à lista

### Funcionalidades Técnicas

- Dados persistidos localmente com **Room Database** (migração de schema versionada)
- Arquitetura **MVVM** com separação em camadas: `model/`, `view/`, `viewmodel/`
- Interface construída inteiramente com **Jetpack Compose** e **Material3**
- Navegação implementada com **Navigation Compose**
- Suporte a **modo escuro** — cores de prioridade e datas se adaptam automaticamente ao tema do sistema
- **10 testes unitários** cobrindo o ViewModel e o repositório

---

## Observações Relevantes

- O aplicativo foi desenvolvido seguindo o padrão de repositório com interface (`ITarefaRepository`) para facilitar a testabilidade — o `TaskViewModel` nunca depende diretamente do Room, apenas da abstração.
- A migração do banco de dados (versão 1 → 2) foi implementada sem destruição de dados (`fallbackToDestructiveMigration` não utilizado), garantindo que tarefas criadas em versões anteriores do app sejam preservadas.
- O campo de data usa timestamps UTC para evitar inconsistências de fuso horário ao comparar com a data atual.

---

## Requisitos Técnicos Atendidos

| Requisito | Implementação |
|---|---|
| ≥ 2 telas em Jetpack Compose | `TaskListScreen` + `TaskDetailScreen` |
| Navegação com Navigation Compose | `NavHost` em `MainActivity` |
| Room Database com ≥ 1 entidade | Entidade `Tarefa`, `TarefaDatabase` v2 |
| Arquitetura MVVM | `TaskViewModel` + `TarefaRepository` + `ITarefaRepository` |
| Organização em camadas | Pacotes `model/`, `view/`, `viewmodel/` |
| Desenvolvimento em Kotlin | 100% Kotlin |

---

## Como Executar

1. Abra o projeto no **Android Studio**
2. Aguarde a sincronização do Gradle
3. Execute em um emulador ou dispositivo com **Android 7.0 (API 24)** ou superior
