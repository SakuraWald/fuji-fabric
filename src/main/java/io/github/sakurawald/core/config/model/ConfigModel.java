package io.github.sakurawald.core.config.model;

import io.github.sakurawald.core.job.impl.MentionPlayersJob;
import io.github.sakurawald.core.structure.RegexRewriteEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ConfigModel {

    public Core core = new Core();
    public Modules modules = new Modules();

    public static class Core {

        public Debug debug = new Debug();
        public Backup backup = new Backup();
        public Language language = new Language();
        public Quartz quartz = new Quartz();

        public static class Quartz {
            public String logger_level = "WARN";
        }

        public static class Backup {

            public int max_slots = 15;
            public List<String> skip = new ArrayList<>() {
                {
                    this.add("head");
                }
            };
        }

        public static class Language {
            public String default_language = "en_US";
        }

        public static class Debug {
            public boolean disable_all_modules = false;
        }
    }

    @SuppressWarnings("unused")
    public static class Modules {
        public Config config = new Config();
        public Language language = new Language();
        public Chat chat = new Chat();
        public Placeholder placeholder = new Placeholder();
        public MOTD motd = new MOTD();
        public Nametag nametag = new Nametag();
        public TabList tab_list = new TabList();
        public Tpa tpa = new Tpa();
        public Back back = new Back();
        public Home home = new Home();
        public Pvp pvp = new Pvp();
        public Afk afk = new Afk();
        public Rtp rtp = new Rtp();
        public Works works = new Works();
        public DeathLog deathlog = new DeathLog();
        public View view = new View();
        public Echo echo = new Echo();
        public Functional functional = new Functional();
        public SystemMessage system_message = new SystemMessage();
        public Cleaner cleaner = new Cleaner();
        public CommandScheduler command_scheduler = new CommandScheduler();
        public CommandPermission command_permission = new CommandPermission();
        public CommandRewrite command_rewrite = new CommandRewrite();
        public CommandAlias command_alias = new CommandAlias();
        public CommandAttachment command_attachment = new CommandAttachment();
        public CommandInteractive command_interactive = new CommandInteractive();
        public CommandWarmup command_warmup = new CommandWarmup();
        public CommandCooldown command_cooldown = new CommandCooldown();
        public CommandToolbox command_toolbox = new CommandToolbox();
        public CommandSpy command_spy = new CommandSpy();
        public CommandEvent command_event = new CommandEvent();
        public World world = new World();
        public TeleportWarmup teleport_warmup = new TeleportWarmup();
        public TopChunks top_chunks = new TopChunks();
        public Skin skin = new Skin();
        public WorldDownloader world_downloader = new WorldDownloader();
        public Whitelist whitelist = new Whitelist();
        public Head head = new Head();
        public Profiler profiler = new Profiler();
        public Tester tester = new Tester();
        public Multiplier multiplier = new Multiplier();
        public Disabler disabler = new Disabler();
        public AntiBuild anti_build = new AntiBuild();
        public Color color = new Color();
        public Kit kit = new Kit();
        public TempBan temp_ban = new TempBan();
        public CommandMeta command_meta = new CommandMeta();
        public Gameplay gameplay = new Gameplay();

        public static class World {
            public boolean enable = false;
        }

        public static class MOTD {
            public boolean enable = false;
        }

        public static class Nametag {
            public boolean enable = false;
        }

        public static class TeleportWarmup {
            public boolean enable = false;

            public int warmup_second = 3;

            public double interrupt_distance = 1d;

            public Dimension dimension = new Dimension();

            public static class Dimension {
                public Set<String> list = new HashSet<>() {
                    {
                        this.add("minecraft:overworld");
                        this.add("minecraft:the_nether");
                        this.add("minecraft:the_end");
                    }
                };
            }
        }

        public static class CommandCooldown {
            public boolean enable = false;

            public HashMap<String, Long> regex2ms = new HashMap<>() {
                {
                    this.put("rw tp (overworld|the_nether|the_end)", 120 * 1000L);
                    this.put("chunks\\s*", 60 * 1000L);
                    this.put("download\\s*", 120 * 1000L);
                }
            };
        }

        public static class CommandWarmup {
            public boolean enable = false;

            public HashMap<String, Integer> regex2ms = new HashMap<>() {
                {
                    this.put("back", 3 * 1000);
                }
            };
        }

        public static class TopChunks {
            public boolean enable = false;


            public Top top = new Top();
            public int nearest_distance = 128;
            public boolean hide_location = true;
            public HashMap<String, Integer> type2score = new HashMap<>() {
                {
                    this.put("default", 1);
                    this.put("block.minecraft.chest", 1);
                    this.put("block.minecraft.trapped_chest", 2);
                    this.put("block.minecraft.barrel", 1);
                    this.put("block.minecraft.furnace", 3);
                    this.put("block.minecraft.blast_furnace", 4);
                    this.put("block.minecraft.smoker", 3);
                    this.put("block.minecraft.jukebox", 35);
                    this.put("block.minecraft.beacon", 35);
                    this.put("block.minecraft.conduit", 40);
                    this.put("block.minecraft.hopper", 8);
                    this.put("block.minecraft.piston", 10);
                    this.put("block.minecraft.dispenser", 10);
                    this.put("block.minecraft.dropper", 10);
                    this.put("block.minecraft.comparator", 5);
                    this.put("block.minecraft.daylight_detector", 25);
                    this.put("block.minecraft.beehive", 15);
                    this.put("block.minecraft.mob_spawner", 100);
                    this.put("entity.minecraft.player", 15);
                    this.put("entity.minecraft.falling_block", 10);
                    this.put("entity.minecraft.zombie", 4);
                    this.put("entity.minecraft.zombie_villager", 8);
                    this.put("entity.minecraft.zombified_piglin", 5);
                    this.put("entity.minecraft.zoglin", 8);
                    this.put("entity.minecraft.ravager", 80);
                    this.put("entity.minecraft.pillager", 20);
                    this.put("entity.minecraft.evoker", 20);
                    this.put("entity.minecraft.vindicator", 20);
                    this.put("entity.minecraft.vex", 20);
                    this.put("entity.minecraft.piglin", 2);
                    this.put("entity.minecraft.drowned", 2);
                    this.put("entity.minecraft.guardian", 6);
                    this.put("entity.minecraft.spider", 2);
                    this.put("entity.minecraft.skeleton", 2);
                    this.put("entity.minecraft.creeper", 3);
                    this.put("entity.minecraft.endermite", 5);
                    this.put("entity.minecraft.enderman", 4);
                    this.put("entity.minecraft.wither", 55);
                    this.put("entity.minecraft.villager", 25);
                    this.put("entity.minecraft.sheep", 5);
                    this.put("entity.minecraft.cow", 3);
                    this.put("entity.minecraft.mooshroom", 3);
                    this.put("entity.minecraft.chicken", 3);
                    this.put("entity.minecraft.panda", 5);
                    this.put("entity.minecraft.wolf", 8);
                    this.put("entity.minecraft.cat", 8);
                    this.put("entity.minecraft.bee", 15);
                    this.put("entity.minecraft.boat", 5);
                    this.put("entity.minecraft.chest_boat", 5);
                    this.put("entity.minecraft.item_frame", 3);
                    this.put("entity.minecraft.glow_item_frame", 3);
                    this.put("entity.minecraft.armor_stand", 10);
                    this.put("entity.minecraft.item", 10);
                    this.put("entity.minecraft.experience_orb", 3);
                    this.put("entity.minecraft.tnt", 70);
                    this.put("entity.minecraft.hopper_minecart", 20);
                }
            };

            public static class Top {
                public int rows = 10;
                public int columns = 10;
            }
        }

        public static class Chat {
            public boolean enable = false;

            public String format = "<#B1B2FF>[%fuji:player_playtime%\uD83D\uDD25 %fuji:player_mined%⛏ %fuji:player_placed%\uD83D\uDD33 %fuji:player_killed%\uD83D\uDDE1 %fuji:player_moved%\uD83C\uDF0D]<reset> <<dark_green><click:suggest_command:'/msg %player:name% '><hover:show_text:'Time: %fuji:date%<newline><italic>Click to Message'>%player:displayname_visual%</hover></click></dark_green>> %message%";

            public Rewrite rewrite = new Rewrite();
            public MentionPlayersJob.MentionPlayer mention_player = new MentionPlayersJob.MentionPlayer();
            public Display display = new Display();
            public History history = new History();
            public Spy spy = new Spy();

            public static class History {
                public boolean enable = true;

                public int buffer_size = 50;
            }

            public static class Display {

                public boolean enable = true;

                public int expiration_duration_s = 3600;
            }

            public static class Rewrite {
                public List<RegexRewriteEntry> regex = new ArrayList<>() {
                    {
                        this.add(new RegexRewriteEntry("^BV(\\w{10})", "<underline><blue><hover:show_text:'$1'><click:open_url:'https://www.bilibili.com/video/BV$1'>bilibili $1</click></hover></blue></underline>"));
                        this.add(new RegexRewriteEntry("(?<=^|\\s)item(?=\\s|$)", "%fuji:item%"));
                        this.add(new RegexRewriteEntry("(?<=^|\\s)inv(?=\\s|$)", "%fuji:inv%"));
                        this.add(new RegexRewriteEntry("(?<=^|\\s)ender(?=\\s|$)", "%fuji:ender%"));
                        this.add(new RegexRewriteEntry("(?<=^|\\s)pos(?=\\s|$)", "%fuji:pos%"));
                        this.add(new RegexRewriteEntry("((https?)://[^\\s/$.?#].\\S*)", "<underline><blue><hover:show_text:'$1'><click:open_url:'$1'>$1</click></hover></blue></underline>"));
                    }
                };
            }

            public static class Spy {
                public boolean output_unparsed_message_into_console = false;
            }

        }

        public static class Skin {
            public boolean enable = false;
        }

        public static class Back {
            public boolean enable = false;

            public double ignore_distance = 32d;
        }

        public static class Tpa {
            public boolean enable = false;
            public int timeout = 300;
            public MentionPlayersJob.MentionPlayer mention_player = new MentionPlayersJob.MentionPlayer();
        }

        public static class Works {
            public boolean enable = false;

            public int sample_time_ms = 60 * 1000 * 60;
            public int sample_distance_limit = 512;
            public int sample_counter_top_n = 20;
        }

        public static class WorldDownloader {
            public boolean enable = false;

            public String url_format = "http://localhost:%port%%path%";

            public int port = 22222;

            public int bytes_per_second_limit = 128 * 1000;

            public int context_cache_size = 5;
        }

        public static class Disabler {
            public boolean enable = false;

            public ChatSpeedDisabler chat_speed_disabler = new ChatSpeedDisabler();
            public MoveSpeedDisabler move_speed_disabler = new MoveSpeedDisabler();
            public MoveWronglyDisabler move_wrongly_disabler = new MoveWronglyDisabler();
            public MaxPlayerDisabler max_player_disabler = new MaxPlayerDisabler();

            public static class ChatSpeedDisabler {
                public boolean enable = false;
            }

            public static class MoveSpeedDisabler {
                public boolean enable = false;
            }

            public static class MoveWronglyDisabler {
                public boolean enable = false;
            }

            public static class MaxPlayerDisabler {
                public boolean enable = false;
            }
        }

        public static class DeathLog {
            public boolean enable = false;
        }

        public static class Echo {
            public boolean enable = false;

            public SendMessage send_message = new SendMessage();
            public SendBroadcast send_broadcast = new SendBroadcast();
            public SendActionBar send_actionbar = new SendActionBar();
            public SendTitle send_title = new SendTitle();
            public SendToast send_toast = new SendToast();
            public SendChat send_chat = new SendChat();

            public static class SendMessage {
                public boolean enable = true;
            }

            public static class SendBroadcast {
                public boolean enable = true;
            }

            public static class SendActionBar {
                public boolean enable = true;
            }

            public static class SendTitle {
                public boolean enable = true;
            }

            public static class SendToast {
                public boolean enable = true;
            }

            public static class SendChat {
                public boolean enable = true;
            }
        }

        public static class View {
            public boolean enable = false;
        }

        public static class Placeholder {
            public boolean enable = false;
        }

        public static class Pvp {
            public boolean enable = false;
        }

        public static class Whitelist {
            public boolean enable = false;
        }

        public static class CommandPermission {
            public boolean enable = false;
        }

        public static class Head {

            public boolean enable = false;
        }

        public static class Profiler {
            public boolean enable = false;
        }

        public static class CommandSpy {
            public boolean enable = false;
        }

        public static class CommandScheduler {
            public boolean enable = false;
        }

        public static class Config {
            public boolean enable = false;
        }

        public static class Tester {
            public boolean enable = false;
        }

        public static class Language {
            public boolean enable = false;
        }

        public static class Afk {
            public boolean enable = false;

            public AfkEffect effect = new AfkEffect();

            public static class AfkEffect {
                public boolean enable = true;

            }

        }

        public static class Rtp {
            public boolean enable = false;
        }

        public static class CommandInteractive {
            public boolean enable = false;
        }

        public static class Home {
            public boolean enable = false;
        }

        public static class SystemMessage {
            public boolean enable = false;
        }

        public static class CommandAlias {
            public boolean enable = false;
        }

        public static class CommandAttachment {
            public boolean enable = false;
        }

        public static class CommandRewrite {
            public boolean enable = false;
        }

        public static class Multiplier {
            public boolean enable = false;
        }

        public static class AntiBuild {
            public boolean enable = false;

        }

        public static class Color {
            public boolean enable = false;

            public Sign sign = new Sign();
            public Anvil anvil = new Anvil();

            public static class Sign {
                public boolean enable = true;
            }

            public static class Anvil {
                public boolean enable = true;
            }
        }

        public static class Functional {
            public boolean enable = false;

            public Workbench workbench = new Workbench();
            public Enchantment enchantment = new Enchantment();
            public GrindStone grindstone = new GrindStone();
            public StoneCutter stonecutter = new StoneCutter();
            public Anvil anvil = new Anvil();
            public Cartography cartography = new Cartography();
            public EnderChest enderchest = new EnderChest();
            public Smithing smithing = new Smithing();
            public Loom loom = new Loom();

            public static class Workbench {
                public boolean enable = false;
            }

            public static class Enchantment {
                public boolean enable = false;
            }

            public static class GrindStone {
                public boolean enable = false;
            }

            public static class StoneCutter {

                public boolean enable = false;
            }

            public static class Anvil {

                public boolean enable = false;
            }

            public static class Cartography {
                public boolean enable = false;
            }

            public static class EnderChest {
                public boolean enable = false;
            }

            public static class Smithing {
                public boolean enable = false;
            }

            public static class Loom {
                public boolean enable = false;
            }

        }

        public static class Gameplay {
            public boolean enable = false;

            public MultiObsidianPlatform multi_obsidian_platform = new MultiObsidianPlatform();
            public Carpet carpet = new Carpet();

            public static class Carpet {
                public boolean enable = false;

                public FakePlayerManager fake_player_manager = new FakePlayerManager();
                public BetterInfo better_info = new BetterInfo();

                public static class FakePlayerManager {
                    public boolean enable = false;
                }

                public static class BetterInfo {
                    public boolean enable = false;
                }
            }

            public static class MultiObsidianPlatform {
                public boolean enable = false;
            }
        }


        public static class CommandMeta {
            public boolean enable = false;

            public Run run = new Run();
            public ForEach for_each = new ForEach();
            public Chain chain = new Chain();
            public Delay delay = new Delay();
            public Json json = new Json();
            public Attachment attachment = new Attachment();
            public Shell shell = new Shell();

            public static class Run {
                public boolean enable = false;
            }

            public static class ForEach {
                public boolean enable = false;
            }

            public static class Chain {
                public boolean enable = false;
            }

            public static class Delay {
                public boolean enable = false;
            }

            public static class Json {
                public boolean enable = false;
            }

            public static class Attachment {
                public boolean enable = false;
            }

            public static class Shell {
                public boolean enable = false;
            }

        }

        public static class CommandToolbox {
            public boolean enable = false;
            public Bed bed = new Bed();
            public Extinguish extinguish = new Extinguish();
            public Feed feed = new Feed();
            public Fly fly = new Fly();
            public God god = new God();
            public Hat hat = new Hat();
            public Sit sit = new Sit();
            public Heal heal = new Heal();
            public Lore lore = new Lore();
            public More more = new More();
            public Ping ping = new Ping();
            public Realname realname = new Realname();
            public Nickname nickname = new Nickname();
            public Repair repair = new Repair();
            public Reply reply = new Reply();
            public Seen seen = new Seen();
            public Suicide suicide = new Suicide();
            public Top top = new Top();
            public TrashCan trashcan = new TrashCan();
            public Tppos tppos = new Tppos();
            public Warp warp = new Warp();
            public Burn burn = new Burn();
            public HelpOp help_op = new HelpOp();
            public Near near = new Near();
            public Jump jump = new Jump();
            public Compass compass = new Compass();

            public static class Bed {
                public boolean enable = false;
            }

            public static class Extinguish {
                public boolean enable = false;
            }

            public static class Feed {
                public boolean enable = false;
            }

            public static class Fly {
                public boolean enable = false;
            }

            public static class God {
                public boolean enable = false;
            }

            public static class Hat {
                public boolean enable = false;
            }

            public static class Heal {
                public boolean enable = false;
            }

            public static class Lore {
                public boolean enable = false;
            }

            public static class Sit {
                public boolean enable = false;
            }

            public static class More {
                public boolean enable = false;
            }

            public static class Ping {
                public boolean enable = false;
            }

            public static class Realname {
                public boolean enable = false;

            }

            public static class Nickname {
                public boolean enable = false;
            }

            public static class Repair {
                public boolean enable = false;

            }

            public static class Reply {
                public boolean enable = false;
            }

            public static class Seen {
                public boolean enable = false;
            }

            public static class Suicide {
                public boolean enable = false;
            }

            public static class Top {
                public boolean enable = false;
            }

            public static class TrashCan {
                public boolean enable = false;
            }

            public static class Tppos {
                public boolean enable = false;
            }

            public static class Warp {
                public boolean enable = false;
            }

            public static class Burn {
                public boolean enable = false;
            }

            public static class HelpOp {
                public boolean enable = false;
            }

            public static class Near {
                public boolean enable = false;
            }

            public static class Jump {
                public boolean enable = false;
            }

            public static class Compass {
                public boolean enable = false;
            }
        }

        public static class TabList {
            public boolean enable = false;
            public Sort sort = new Sort();
            public Faker faker = new Faker();

            public static class Sort {
                public boolean enable = false;

                public SyncGameProfile sync_game_profile = new SyncGameProfile();
                public static class SyncGameProfile {
                    public boolean enable = true;
                }
            }

            public static class Faker {
                public boolean enable = false;
            }
        }

        public static class Kit {
            public boolean enable = false;
        }

        public static class TempBan {
            public boolean enable = false;
        }

        public static class CommandEvent {

            public boolean enable = false;

        }

        public static class Cleaner {
            public boolean enable = false;
        }

    }

}
