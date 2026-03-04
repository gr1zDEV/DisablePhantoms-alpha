# DisablePhantoms

DisablePhantoms is a lightweight Bukkit-compatible plugin that lets you control **where phantoms are allowed to spawn** on your server.

Perfect for network setups and survival servers that want phantoms in specific worlds only.

---

## ✨ Features

- Per-world phantom spawn control
- Two flexible modes:
  - **BLACKLIST**: disable phantoms in listed worlds
  - **WHITELIST**: only allow phantoms in listed worlds
- In-game admin command for quick management
- Tab completion for subcommands and world names
- Optional console logging for blocked phantom spawns
- Built for **Minecraft 1.20+** across Bukkit, Spigot, Paper, Purpur, and Folia

---

## 📦 Compatibility

- **Server software:** Bukkit, Spigot, Paper, Purpur, Folia
- **Minecraft version:** 1.20+
- **Java version:** 17+

---

## 🚀 Installation

1. Download `DisablePhantoms-<version>.jar`
2. Place it in your server's `plugins/` folder
3. Start (or restart) your server
4. Edit `plugins/DisablePhantoms/config.yml` if needed
5. Run `/disablephantoms reload`

---

## ⚙️ Configuration

Default `config.yml`:

```yml
# mode: BLACKLIST = phantoms are disabled in the listed worlds
#       WHITELIST = phantoms are ONLY allowed in the listed worlds
mode: BLACKLIST

# List of worlds affected by the mode above
worlds:
  - world

# Log blocked phantom spawns to console (useful for debugging)
log-blocked-spawns: false
```

### Mode behavior

- **BLACKLIST**
  - Worlds in `worlds` ➜ phantoms blocked
  - Worlds not in `worlds` ➜ phantoms allowed

- **WHITELIST**
  - Worlds in `worlds` ➜ phantoms allowed
  - Worlds not in `worlds` ➜ phantoms blocked

> World names are stored and compared case-insensitively.

---

## ⌨️ Commands

Base command: `/disablephantoms`

- `/disablephantoms status` — show active mode and configured worlds
- `/disablephantoms list` — show enabled/disabled phantom status for loaded worlds
- `/disablephantoms add <world>` — add a world to the config list
- `/disablephantoms remove <world>` — remove a world from the config list
- `/disablephantoms reload` — reload plugin configuration

---

## 🔐 Permission

- `disablephantoms.admin` (default: OP)
  - Grants access to all DisablePhantoms commands

---

## 🧩 plugin.yml summary

- Plugin name: `DisablePhantoms`
- Main class: `com.ezinnovations.disablephantoms.DisablePhantoms`
- API version: `1.20`
- Folia supported: `true`

---

## 🛠️ Build from source

```bash
mvn clean package
```

Compiled JAR will be in `target/`.

---

## 📄 License

No license file is currently included in this repository. Add one before redistribution if required by your platform.
