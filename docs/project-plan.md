# 项目计划

## 任务分配
### 阶段开发任务

#### 第0阶段

梳理原型系统的代码和流程，为正式开发做好准备。

#### 第1阶段

完成从下投资指令开始到投资指令处理完成的整个流程的基本实现。

* 投资服务
  * 多资管公司支持
  * 创建投资指令草稿
  * 更新指令草稿
  * 提交投资指令
  * 创建委托分配计划
  * 分配交易员
* 交易服务
  * 实现上证A股竞价交易服务
  * 计算投资指令所需资源（钱、仓）
  * 提交资源申请单至资源队列
  * 产生虚拟成交
  * 成交处理
  * 基本交易参数处理
  * 基本交易日历处理
  * 基本的盘后清算
* 资金持仓服务
  * 从资源队列获取新的资源申请单并进行处理
  * 资金持仓服务将处理结果告知交易服务
  * 基本的盘后清算
* 行情服务
  * 实现行情接口
* Web前台
  * 投资决策界面基本功能
  * 交易员界面基本功能
  * 投资台帐基本功能

#### 第2阶段

对系统进行进一步的完善和打磨。

* 更多交易服务、事务服务的实现
* 增加标的组支持
* 完善委托分配计划
* 报盘服务实现
* 行情服务实现
* 认证权限服务实现
* 风控服务实现
* 数据分析、报表服务实现
* Web界面的细节打磨

### 并行任务

并行任务将贯穿各个阶段。部分并行任务与阶段开发任务有相互依赖关系。

#### 开发环境搭建

理论上来说，开发环境搭建应该在大规模开发之前完成。在实际开发过程中，由于资源限制，可以考虑开发先在外网环境启动。内网开发环境搭建同步进行。待开发环境搭建完成后，项目整体由外网迁移至内网。搭建过程中需要考虑到迁移时尽量少修改项目本身。

* 服务器资源准备
* 桌面端资源准备
* 版本管理工具
* 持续集成工具
* docker仓库
* maven仓库
* npm仓库
* 开发测试环境（？）

#### 持续集成流程实施

目前我们的持续集成流程主要是针对测试与发布。因此理论上来书可以在测试介入之前完成。但目前发现整个系统资源占用较大，实际开发时可能需要有一个独立的测试环境供给开发人员自己测试用，而测试环境的部署也可以纳入持续集成流程进行自动化管理。因此持续集成流程完成得越早越好。持续集成流程在原型阶段有初步的实现，完整的实现可以基于此进行完善。部署流程设计可以跟项目开发同时进行。流程设计完成后即可介入开发流程，跟随项目发展逐步演进。在流程设计完成之前

* 在持续集成工具上配置开发，实现之前设计的持续集成流程
* 开发测试环境部署流程的设计
* 生产环境部署流程的设计
* 流程配套脚本的开发
* 相关资源的版本管理方案

#### 测试方案及实施

新架构中计划的发布模式为持续发布，因此原系统那种一个版本发布前开始测试的模式不适合在新架构中使用。在发布开始后，测试和发布将是一个常态化过程。测试人员需要在首次发布前规划好整个测试方案。方案将以自动化回归测试为主，辅以人工测试，配合持续集成尽可能自动化的完成。一个测试的周期通常会在1-2天内就结束。测试方案的设计可以在项目基本成型后开始，方案设计完成后配合持续集成工具介入，跟随项目功能的逐步完善而演进。

这里也要提一下单元测试方案。在本系统中，单元测试应该可以起到两个作用，第一个作用是保证软件开发的最基本的质量，第二个作用是在依赖服务不可用的情况下进行有效开发。这也是单元测试方案在设计时需要注意的两点。单元测试方案的设计由开发组负责。

* 单元测试方案的设计
* 集成测试方案的设计
* 在持续集成工具上配置开发
* 流程配套脚本的开发
* 测试用例的编写
* 相关资源的版本管理方案

#### 运维方案及实施

新系统是基于微服务架构进行设计的，在微服务架构中，由于系统的复杂性和远程相互依赖，运维是整个系统正常运行不可或缺的一部分。运维方案需要在产品正式发布前完成。并且运维方案也需要同步部署到测试环境中作为测试环境的一部分。在原型开发时仅仅简单涉及了服务发现和服务编排。正式环境中需要更完善的服务编排工具。在做运维方案设计时，需要考虑服务器资源的问题。目前客户的服务器资源是极为有限的，可能只有一台最多两台服务器。因此运维工具自身不能占用太多资源。另外，需要考虑公司集中运维的方案。运维方案可以跟项目开发同步进行。随时可以介入。

* 运维方案的设计相关工具的部署与配置
* ·配套脚本的开发
* 相关资源的版本管理方案

#### 培训

新系统采用了目前较新的技术，因此对于开发人员、测试人员、运维人员和实施人员的培训也是必不可少的。开发培训需要在大规模开发之前完成。其他培训视情况。

* **系统架构培训**
* **Java端开发培训**
* **前端开发培训**
* 测试技术培训
* 运维技术培训

所有的培训都分成方案设计及实施两个部分。

#### 项目管理

目前在小团队中没有严谨的项目管理方案，基本上以敏捷模式为主。大规模开发时需要更严谨的项目管理方案。项目管理方案可以与开发并行设计，设计完成后介入，并在开发过程中逐步演进。

* 项目管理方案设计
* 项目管理工具部署及配置

- 项目计划制定与跟踪

## 工作量预估

### 开发任务

#### 第0阶段

前后台部分可以并行处理。整个第0阶段5天可以完成。

* 后台原型代码整理5人天


* 前台原型代码整理5人天

#### 第1阶段

前后台由不同开发人员负责。前台开发依赖后台，但前台开发量相对较少，基本上可以紧跟后台进度。这里将前后台的工作量设置成相同时间。整个第1阶段计划3周完成。需要注意的是，一部分因为工作量而暂时不实现的功能，需要考虑事先预留接口。以减少后续完善时的改动。

* 后台基本流程实现15人天
* 前台基本流程界面实现15人天

#### 第2阶段

第2阶段是功能全面铺开，开发人员大规模参与的一个阶段，大任务比较多，任务涉及的细节也多，很难清楚的估计工作量。这里仅对各服务展开分析，并给出大致的工作量范围。具体工作量日后再确定。

##### 投资服务

投资服务在第1阶段开发完成后大致成型，第2阶段主要是补完的工作。这里的工作量相对于整个第2阶段工作量可以暂时忽略不计。

##### 交易服务

交易服务需要完整实现以下功能：

```
上证A股竞价交易服务
上证A股首次网上公开发行交易服务
上证港股通竞价交易服务
上证基金竞价交易服务
上证交易型货币基金申赎交易服务
上证股票ETF申赎交易服务
上证债券ETF申赎交易服务
上证LOF场内申赎交易服务
上证债券竞价交易服务
上证债券质押式回购竞价交易服务
上证股票期权竞价交易服务

深证A股竞价交易服务
深证A股首次网上公开发行交易服务
深证港股通竞价交易服务
深证基金竞价交易服务
深证交易型货币基金申赎交易服务
深证股票ETF申赎交易服务
深证黄金ETF申赎交易服务
深证债券ETF申赎交易服务
深证LOF场内申赎交易服务
深证债券竞价交易服务
深证债券质押式回购竞价交易服务

中金所股指期货竞价交易服务
中金所国债期货竞价交易服务
郑商所商品期货竞价交易服务
大商所商品期货竞价交易服务
上期所商品期货竞价交易服务

港交所股票竞价交易服务
自定义标的交易服务
```

共29个。

在第1阶段中，上证A股竞价交易服务应该已经有了一个基本的实现。因此在第2阶段中，我们一方面需要进一步完善这个实现，另一方面，可以以这个实现为蓝本实现其他的交易服务。

在第2阶段，我们需要完善交易服务的地方主要是：

* 标的组功能。标的组的引入涉及标的组的生成，否则没法实际使用。而标的组的生成又依赖标的基础信息，标的基础信息是从行情服务获得，所以必须依赖行情服务的实现。
* 完整的清算服务。清算服务也依赖行情。还依赖交易参数（保证金等），部分交易服务的清算还依赖额外信息（如大边保证金计算时需要的套利组合信息），需要额外开发。
* 完整的交易参数功能。
* 完整的交易日历功能。
* 其他。

在单个交易服务完善的基础上，以单个交易服务为蓝本，实现其他交易服务相对比较方便。一部分交易服务有类似性，只需要复制代码做一些简单的调整。一部分交易服务可能需要从头设计，从头开发。

交易服务的另一个特点是基本上可以完全并行开发。不太可能产生冲突。因此可以通过投入更多资源来加速开发。但前提是单一的交易服务已经开发完善。

单一交易服务开发完善依赖行情服务。假设行情服务已经开发完成，估计单一交易服务完善需要30人天。

每个服务平均理解需求需要1人天，详细设计1人天，开发5人天。

然后按全部交易服务的数量，这些交易服务开发完成大约需要210人天。

##### 资金持仓服务

资金持仓服务需要完整实现以下功能：

```
上证A股持仓服务
上证债券持仓服务
上证基金持仓服务
深证A股持仓服务
深证债券持仓服务
深证基金持仓服务
上期所商品期货持仓服务
大商所商品期货持仓服务
郑商所商品期货持仓服务
中金所股指期货持仓服务
中金所国债期货持仓服务

普通资金账户资金服务
期货保证金账户资金服务
信用资金账户资金服务
自定义标的资金账户资金服务
```

共15个服务。

在第1阶段中，上证A股持仓服务金和普通资金账户资金服务应该已经有了一个基本的实现。因此在第2阶段中，我们一方面需要进一步完善这两个实现，另一方面，可以以这两个实现为蓝本实现其他的资金和持仓服务。

完善上证A股持仓服务和普通资金账户资金服务主要工作是全面实现在第1阶段没有实现的指标。以及资金服务参数功能的实现。这类实现估计需要15人天。

在上述两个服务完善之后，其他的服务可以参考这两个服务进行开发，效率较高。

每个服务平均理解需求需要1人天，详细设计1人天，开发5人天。

所有服务大致需要105人天。

资金持仓服务与交易服务一样，持仓服务开发相对独立，可以并行。可以通过增加资源的形式加快开发速度。

##### 时序服务

时序服务在原型开发阶段已经提供了序号(版本号)的功能，在第1阶段开发时应该已经加入了定时触发的功能。（因为需要实现清算）。

在第2阶段，时序服务可以暂时不做改进，或者在有多余资源的情况下，做高可靠性方面的增强工作。

目前暂时不计算工作量。

##### 行情服务

在第2阶段，鉴于不少功能都依赖行情服务，行情服务的优先级是比较高的。行情服务对其他服务基本没有依赖。

行情服务在第1阶段应该已经定义好了接口。依赖行情服务的功能可以暂时先根据接口进行开发。但行情仍然需要尽快可以拿出可以获取实际数据的版本。

行情服务分成公共行情服务和系统行情服务两个组件。两个组件间也以接口的形式进行通讯。但鉴于两个组件联系的紧密性，建议交给单个开发人员或开发小组进行开发。避免协作上的不流畅。

公共行情服务主要负责

* 收取行情源数据
* 接受系统行情服务的实时行情订阅请求（支持增量订阅）
* 根据订阅请求推送相应标的的实时行情快照（一次）及后续行情变化
* 接受系统行情服务的实时行情查询请求
* 返回实时行情查询结果
* 接受系统行情服务的标的基础信息查询请求
* 返回标的基础信息查询结果

系统行情服务主要负责

* 提供标的基础信息查询接口，转发标的基础信息查询结果
* 提供实时行情查询接口，转发实时行情查询结果
* 将收取到的实时行情保存为历史，并提供历史行情查询接口
* 提供实时行情订阅接口，将不同的订阅请求合并后向公共行情服务进行订阅
* 将从公共行情服务收到的实时行情推送转推给各个订阅源。

整个开发在行情源已经准备完成的情况下，详细设计参考现有系统，大致估计5天。开发大致估计在60人天。但由于行情源是第三方接口，接口联调的时间难以估计。

##### 报盘服务

报盘服务主要负责跟柜台或PB系统的交互。按现有系统的状况，可以分两个阶段。第一个阶段是完成成交回报的获取，以及保证金信息的获取。第二个阶段是完成委托的处理。第一阶段应该尽早完成。

报盘服务需要完成：

* 成交回报的获取与推送
* 成交回报的查询
* 保证金信息的获取与推送
* 保证金信息的查询
* *委托处理*
* *撤单处理*

详细设计参考现有系统，大致估计5天。开发第一阶段大致估计在30人天，第二阶段大致估计在10人天。根据对接系统API的不同，接口联调难度也不同，具体联调时间难以估计。

##### 风控服务

主要用于风控。风控系统相对独立，优先级相对较低。

由投资服务触发启动。风控服务启动后向其他服务请求相关指标并进行风险判断。判断完成后向投资服务及前台推送风控结果，同时将风控结果保存备查。

需要完成的工作：

- 实现启动、停止风控接口
- 实现查询风控结果的接口
- 实现风控Lua脚本执行环境
- 实现风控Lua脚本的上传及管理
- 调用其他组件的接口获得相关指标
- 向前台通知风控结果

其中集成Lua脚本执行环境需要研究，最理想的情况，风控Lua脚本可以复用现有系统的，不需要改动；不理想的情况是Lua脚本需要根据新的运行环境做调整。在不增加新风控脚本的情况下，时间大致估计在40-50人天。

##### 产品管理服务

产品管理服务相对独立，但细节较多。产品管理服务是众多服务的基础信息来源，优先级较高。但产品管理服务可以渐进式开发，优先开发其他服务所需的核心信息。周边的信息可以暂缓。

产品管理服务主要就是数据库的增删改查，没有技术上的难点。并且现在已经实现了一部分核心的数据提供。

在第1阶段中，产品管理服务应能提供基础流程所需的核心数据。

第2阶段，产品管理服务可以逐步完善。

完整的开发周期应该较长，但应该可以及时拿出其他服务所需的接口。由于具体任务较多，目前详细设计总体预估为10人天，开发工作量预估为80人天。

##### 权限认证服务

权限认证服务需要重新做一个完整通用的设计，这个周期会相对较长。实现方面应该不会有太多技术上的难点。可以考虑基于现有的权限认证框架如Spring Security、Apache Shiro等进行开发。

权限认证开发完成后，相关服务都需要进行接口的调整。由于权限认证服务的接口无法在短期内确定，只能在设计完善后再进行修改，这里也需要一定的工作量。

权限认证服务本身大致估计的工作量是详细设计10人天，开发30人天。其他服务的跟随修改视当时的开发规模而定。暂定是60人天。

##### 事务服务

事务服务需要完整实现以下功能：

```
上证A股分红事务服务
上证债券质押式回购质押出库事务服务
上证债券质押式回购质押入库事务服务
上证A股指定交易事务服务

深证A股分红事务服务
深证A股转托管事务服务
深证债券质押式回购质押出库事务服务
深证债券质押式回购质押入库事务服务

中金所股指期货交割事务服务
中金所国债期货交割事务服务
```

共10个。

事务服务在现有系统中没有实现，因此需要重新进行设计。而且每一个事务服务都是不同的，需要独立进行设计。

事务服务目前连总控程序都不存在，可以从交易服务借鉴，完成总控程序，大约需要15人天。

具体事务服务按平均理解需求1人天，详细设计1人天，开发5人天计算，全部完成需要70人天。

事务服务也是可以通过多分配资源来加速开发的。

##### 数据分析、报表服务

数据分析和报表将会是资管系统的重要组成部分和竞争力所在。因此数据分析及报表服务需要作为独立i的系统单独进行分析和设计。目前的想法是基于一些成熟的工具和框架进行开发，以提高开发效率及开发灵活度。在初期选型时，没有进行这部分的选型工作。因此该服务需要从选型开始，工作量会较大。

选型完成之后的目标是

1. 指标计算。

   需要在数据分析服务中实现的指标目前分成 投资组合、基金产品账户、基金经理、基金产品、资管公司 5个层次。

   每个层次包含下列

   ```
   单位净值		
   累计净值		
   基金份额		
   总资产	
   总负债	
   净资产	
   现金	
   可用资金	
   可取资金	

   当日交易费用	
   累计交易费用	
   当日分红收益	
   累计分红收益	
   当日已实现收益	
   累计已实现收益	
   当日收益	
   累计收益	
   净当日收益	
   净累计收益	
   账面收益	
   持仓市值	

   收益率	
   年化收益率	
   超额收益率(市场指数)	
   年化超额收益率(市场指数)	
   波动率	
   Alpha系数(市场指数)	
   Beta系数(市场指数)	
   夏普比率(市场指数)	
   信息比率(市场指数)	
   卡马比率(市场指数)	
   最大回撤率	
   回撤率	
   VaR	
   ```

   共34个指标。

2. 报表实现。

   需要实现目前定义的

   ```
   基金经理业绩
   基金产品期间收益指标
   基金产品运营风险指标
   基金产品业绩指标
   基金产品走势图
   基金产品业绩
   产品账户绩效评价
   产品账户业绩归因
   个股盈亏排名
   大类资产盈亏
   ```

   共11张报表。

根据之前指标开发的经验，选型大致5人天，详细设计15人天，框架开发30人天。

指标计算中，各个层次的相同指标计算公式接近，我们按指标划分，各层的指标不再单独计算工作量。34个指标每一个平均理解需求+详细设计1人天，开发2人天。一共需要大约105人天。

11张报表每张平均理解需求1人天，详细设计1人天，开发3人天。一共需要105人天。

##### Web前端开发

前端开发分为frontend和web gateway两个部分。

frontend 部分按现有系统参考，大约有40个页面。其中有30个属于简单页面，平均工作量为1人天；10个属于复杂页面（如登录、二次认证、交易员等），平均工作量为5人天。其中投资决策界面比较特殊，单独讨论。投资决策界面上需要用到很多额外的技术，如 复制粘贴、导入导出、搜索、快捷键处理、全键盘支持 等，根据现有系统中对这些技术的探索，平均这些技术每项需要10人天去解决，新的系统由于使用了新框架，因此所需的时间接近于现有系统。上述技术一共需要大约50人天，再加上投资决策界面本身属于复杂界面，界面设计也需要大约5人天。frontend部分大致需要 30 + 50 + 55 = 135人天。

gateway部分的工作主要分成以下几个部分：请求转发、查询请求拼装、针对界面功能的权限处理和针对界面的其他接口。请求转发部分工作量可以忽略不记。查询请求拼装在第1阶段应该跑通。在第2阶段，主要是工作量的问题。现在按每个页面的查询接口平均2人天计算，一共需要80人天。针对界面功能的权限处理目前因为权限服务还没有设计和开发，无法提前打通路径，需要在第2阶段进行设计和开发。现在暂时预估详细设计工作量10人天，开发工作量30人天。针对界面的其他接口目前还不能确定有多少。暂时预留30人天的余量。gateway部分的工作大致需要 80+40+30=150人天。

### 并行任务

#### 开发环境搭建

##### 服务器资源准备

服务器资源需要可以联网，方便从网上获取第三方库、docker仓库等资源。需要1-2台。硬盘需求较大（因为要做各种仓库）。内存也最好大一些（16G）。CPU没有特殊要求。

服务器资源本身的准备时间应该很快（1-2人天），但涉及联外网部分牵涉较广，时间无法预估。

##### 桌面端资源准备

Windows对目前新架构所使用的Docker等技术支持不佳，为了提高开发效率，建议使用Linux作为开发环境。

由于VMware工具限制，目前无法像登陆内网Windows开发环境一样直接登陆Linux开发环境。需要先登陆内网Windows然后远程连接至Linux开发环境。这个方案的效率尚未得到验证，需要先部署一个测试环境看一下能否满足日常开发所需。如果方案可行，在原型开发阶段已经有成熟的Linux开发环境模板，迁移到内网应该较快。如果不可行的话，就需要重新设计桌面端开发环境方案，这样就需要更多的时间。方案评估需要3-4人天，方案可行的情况下全面实施大约需要1-2人天。不可行的情况下重新制定方案时间无法预估。

##### 仓库、工具等安装

在原型阶段已经有了一些方案并在阿里云上预演过，按这个方案实施应该较快，估计2-3人天可以完成。

但现有方案里不包含npm仓库，这部分可能时间会更久一些。保守估计5人天。

##### 开发测试方案

目前因为外网资源所限，并没有特别好的开发测试方案，这部分需要重新设计和实施。工作量难以预估。

#### 持续集成流程实施

持续集成方案已经在原型开发时确定。后续主要工作是实施。

目前提交构建流程的主体已经可用，可以先使用。这个部分工作量较小。后续需要迁移至Pipeline脚本，这部分需要研究、评估和实施。预估时间10-15人天。

Pipeline脚本可行的话需要纳入版本管理。版本管理方案设计需要1-2人天。

#### 测试方案及实施

测试方案主要分成单元测试与其他测试两个部分。单元测试主要由开发组负责，需要尽早完成，否则可能会影响开发流畅度。理想的状态是在第2阶段开始前制定完成。大规模开发时直接使用。预估时间15人天。

其他测试原则上可以后期介入。主要由测试组负责。工作量这里不予评估。

#### 运维方案及实施

#### 培训

开发培训需要在大规模开发前进行。可以有效提高开发效率。对于测试、运维、实施人员，需要进行系统架构的培训，对系统架构有良好的理解才能更好的开展对应的方案设计及实施工作。

培训材料的准备可以在第0阶段、第1阶段准备好。每种类别按一个小时的量进行培训安排。

目前阶段需要准备的培训：

* 系统架构培训（面向整个新架构项目组）
* Java开发培训（面向后台开发人员）
* 前端开发培训（面向前端开发人员）

#### 项目管理

项目管理方案设计。目前不评估工作量。

### 预留工作量

#### 单元测试

为了保证代码质量，要求对于关键功能点进行单元测试。单元测试的设计和编写需要占用一定工作量。这里预估单元测试工作量为总体工作量的20%。

#### bug修复

我们需要预留总工作量20%的时间作为bug修复的时间。

#### 其他情况

考虑可能有人员到岗时间差异，以及其他突发状况，我们预留总工作量10%的时间。

### 工作量预估汇总

| 任务分类       | 任务                 | 前置任务          | 工作量预估（单位：人天）  | 是否可并行 |
| ---------- | ------------------ | ------------- | ------------- | ----- |
| 第0阶段       | 后台原型整理             |               | 5             |       |
| 第0阶段       | 前端原型整理             |               | 5             |       |
| 第1阶段       | 后台基本流程开发           |               | 15            |       |
| 第1阶段       | 前端基本界面开发           |               | 15            |       |
| 第2阶段       | 交易服务完善             |               | 30            |       |
| 第2阶段       | 更多交易服务开发           | 交易服务完善        | 210           | 是     |
| 第2阶段       | 资金持仓服务完善           |               | 15            |       |
| 第2阶段       | 更多资金持仓服务开发         |               | 105           | 是     |
| 第2阶段       | 行情服务               |               | 65            |       |
| 第2阶段       | 报盘服务               |               | 45            |       |
| 第2阶段       | 产品管理服务             |               | 90            | 是     |
| 第2阶段       | 权限认证服务             |               | 40+60(相关模块修改) |       |
| 第2阶段       | 风控服务               |               | 40            |       |
| 第2阶段       | 风控脚本               |               | 10            | 是     |
| 第2阶段       | 事务服务核心开发           |               | 15            |       |
| 第2阶段       | 事务服务开发             | 事务服务核心开发      | 70            | 是     |
| 第2阶段       | 数据分析、报表服务框架开发      |               | 50            |       |
| 第2阶段       | 数据分析、报表服务指标实现      | 数据分析、报表服务框架开发 | 105           |       |
| 第2阶段       | 数据分析、报表服务报表实现      | 数据分析、报表服务框架开发 | 105           |       |
| 第2阶段       | Web前端开发 frontend部分 |               | 135           | 是     |
| 第2阶段       | Web前端开发gateway部分   |               | 150           | 是     |
| 并行任务       | 开发环境搭建             |               | 10            |       |
| 并行任务       | 持续集成流程实施           |               | 15            |       |
| 预留工作量      | 单元测试 (1405×0.2)    |               | 281           |       |
| 预留工作量      | bug修复 (1405×0.2)   |               | 281           |       |
| 预留工作量      | 其他情况 (1405×0.1)    |               | 140.5         |       |
| **总    计** |                    |               | 2107.5        |       |



## 资源预估

### 开发资源

* 开发环境服务器(2台)
  * 100G硬盘
  * 16G内存
  * CPU无特殊要求
  * 外网连接
* 开发测试服务器（方案待定）
* 开发桌面环境（每个开发人员一套）
  * Linux 桌面操作系统（Elementory OS Loki）
  * 16-32G内存
  * 硬盘、CPU无特殊要求

### 人力资源

* 项目管理
* 测试
* 集成、运维、部署
* 开发
  * 投资服务、交易服务、资金持仓服务、事务服务
  * 产品管理服务、权限认证服务、时序服务、风控服务、行情服务、报盘服务、数据分析服务
  * Web前端

