# BioSnare - 生物捕捉模组
# BioSnare - Mob Capture Mod

一个简单而有趣的 Minecraft 模组，允许玩家捕捉和收集各种生物。
A simple and fun Minecraft mod that allows players to capture and collect various creatures.

## 功能特点
## Features

### 生物捕捉网 / Bio Snare Net
- 可以捕捉任何生物实体（除了玩家）
- Can capture any living entity (except players)
- 耐久度为10次，每次捕捉消耗1点耐久
- Has 10 durability, consumes 1 durability per capture
- 右键点击生物即可捕捉
- Right-click on a mob to capture it
- 成功捕捉后会生成一个生物球
- Generates a mob ball upon successful capture

### 生物球 / Mob Ball
- 存储被捕捉的生物
- Stores the captured creature
- 显示已捕获生物的类型
- Displays the type of captured creature
- 右键点击方块可以释放生物
- Right-click on a block to release the creature
- 最大堆叠数量为16个
- Maximum stack size of 16

## 合成配方
## Crafting Recipe

### 生物捕捉网 / Bio Snare Net
```
铁锭   粉红色染料 铁锭     Iron Ingot   Pink Dye   Iron Ingot
线     皮革      线       String      Leather    String
铁锭   淡蓝色染料 铁锭     Iron Ingot   Light Blue Iron Ingot
```

需要的材料：
Required Materials:
- 4个铁锭
- 4x Iron Ingots
- 1个粉红色染料
- 1x Pink Dye
- 2个线
- 2x String
- 1个皮革
- 1x Leather
- 1个淡蓝色染料
- 1x Light Blue Dye

## 使用方法
## How to Use

1. 合成生物捕捉网
1. Craft the Bio Snare Net
2. 手持捕捉网右键点击想要捕捉的生物
2. Hold the net and right-click on the creature you want to capture
3. 成功捕捉后会获得一个包含该生物的生物球
3. Upon successful capture, you'll receive a mob ball containing the creature
4. 手持生物球右键点击方块可以释放生物
4. Hold the mob ball and right-click on a block to release the creature

## 安装要求
## Installation Requirements

- Minecraft 1.20.1
- Fabric Loader
- Fabric API

## 语言支持
## Language Support

- 简体中文 (zh_cn)
- Simplified Chinese (zh_cn)
- 英语 (en_us)
- English (en_us)

## 开发者信息
## Developer Information

### 构建项目
### Building the Project
```bash
./gradlew build
```

### 项目结构
### Project Structure
```
src/main/
├── java/com/biosnare/
│   ├── BioSnare.java              # 主类 / Main Class
│   ├── item/
│   │   ├── BioSnareNetItem.java   # 捕捉网物品 / Bio Snare Net Item
│   │   └── MobBallItem.java       # 生物球物品 / Mob Ball Item
│   ├── mixin/
│   │   ├── EntityInteractionMixin.java  # 实体交互处理 / Entity Interaction Handler
│   │   └── MouseMixin.java        # 鼠标事件处理 / Mouse Event Handler
│   └── registry/
│       └── ModItems.java          # 物品注册 / Item Registry
└── resources/
    ├── assets/biosnare/
    │   ├── lang/                  # 语言文件 / Language Files
    │   └── models/                # 物品模型 / Item Models
    └── data/biosnare/
        ├── recipes/               # 合成配方 / Crafting Recipes
        └── advancements/          # 进度文件 / Advancement Files
```

## 许可证
## License

本项目采用 [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.html) 许可证。
This project is licensed under the [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.html).

这意味着你可以：
This means you can:
- 使用、修改和分发本项目的代码
- Use, modify and distribute the code of this project
- 将本项目用于商业用途
- Use this project for commercial purposes

但你必须：
But you must:
- 保持源代码开放
- Keep the source code open
- 在你的项目中使用相同的许可证（GPL-3.0）
- Use the same license (GPL-3.0) in your project
- 标明原始作者和修改信息
- Credit the original author and state changes

详细信息请查看 [LICENSE](LICENSE) 文件或访问 [GNU GPL v3.0 官方网站](https://www.gnu.org/licenses/gpl-3.0.html)。
For detailed information, please check the [LICENSE](LICENSE) file or visit the [GNU GPL v3.0 official website](https://www.gnu.org/licenses/gpl-3.0.html).

## 贡献
## Contributing

欢迎提交 Issues 和 Pull Requests！
Issues and Pull Requests are welcome!

## 致谢
## Acknowledgments

感谢 Fabric 团队提供的优秀模组开发框架。
Thanks to the Fabric team for providing an excellent mod development framework. 