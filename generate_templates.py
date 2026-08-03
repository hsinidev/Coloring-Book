import os
import math

# Output directories
TEMPLATES_DIR = r"D:\my app\Coloring Book\app\src\main\assets\templates"
os.makedirs(TEMPLATES_DIR, exist_ok=True)

kotlin_items = []

def write_vector_file(filename, paths):
    content = [
        '<vector xmlns:android="http://schemas.android.com/apk/res/android"',
        '    android:width="400dp"',
        '    android:height="400dp"',
        '    android:viewportWidth="400"',
        '    android:viewportHeight="400">',
        ''
    ]
    # Add background border
    content.append('    <path')
    content.append('        android:id="bg"')
    content.append('        android:pathData="M 0,0 L 400,0 L 400,400 L 0,400 Z"')
    content.append('        android:strokeColor="#FF000000"')
    content.append('        android:strokeWidth="1"/>')
    
    content.extend(paths)
    content.append('</vector>')
    
    filepath = os.path.join(TEMPLATES_DIR, filename)
    with open(filepath, "w", encoding="utf-8") as f:
        f.write("\n".join(content))

def format_path(path_id, d):
    return f'    <path\n        android:id="{path_id}"\n        android:pathData="{d}"\n        android:strokeColor="#FF000000"\n        android:strokeWidth="2"/>'

# --- 75 Diverse Animals List ---
ANIMAL_NAMES = [
    "Tiger", "Elephant", "Wolf", "Panda", "Bear", "Lion", "Fox", "Deer", "Rabbit", "Squirrel",
    "Owl", "Eagle", "Falcon", "Parrot", "Penguin", "Dolphin", "Whale", "Shark", "Octopus", "Seahorse",
    "Frog", "Chameleon", "Snake", "Crocodile", "Butterfly", "Dragonfly", "Ladybug", "Bee", "Cat", "Dog",
    "Horse", "Cow", "Sheep", "Pig", "Goat", "Chicken", "Duck", "T-Rex", "Triceratops", "Stegosaurus",
    "Pterodactyl", "Dragon", "Unicorn", "Pegasus", "Phoenix", "Monkey", "Gorilla", "Chimpanzee", "Lemur", "Sloth",
    "Koala", "Kangaroo", "Platypus", "Wombat", "Tasmanian Devil", "Giraffe", "Zebra", "Hippo", "Rhino", "Cheetah",
    "Leopard", "Jaguar", "Panther", "Cougar", "Ocelot", "Swan", "Flamingo", "Peacock", "Pelican", "Seagull",
    "Sea Turtle", "Starfish", "Crab", "Lobster", "Jellyfish"
]

# --- 1. ANIMALS GENERATOR (75 Unique Real Animals) ---
def generate_animal(index):
    paths = []
    name = ANIMAL_NAMES[index - 1]
    cx, cy = 200, 200

    # 1. Tiger (Striped Cat)
    if name == "Tiger":
        paths.append(format_path("tiger_body", "M 120,220 C 120,180 280,180 280,220 L 260,320 L 140,320 Z"))
        paths.append(format_path("tiger_head", "M 160,150 Q 200,100 240,150 Q 250,190 200,210 Q 150,190 160,150 Z"))
        paths.append(format_path("tiger_ear_l", "M 160,130 Q 150,100 175,115 Z"))
        paths.append(format_path("tiger_ear_r", "M 240,130 Q 250,100 225,115 Z"))
        paths.append(format_path("tiger_snout", "M 185,175 L 215,175 L 200,190 Z"))
        paths.append(format_path("tiger_stripe_l1", "M 160,155 L 180,160 L 175,165 Z"))
        paths.append(format_path("tiger_stripe_r1", "M 240,155 L 220,160 L 225,165 Z"))
        paths.append(format_path("tiger_stripe_l2", "M 162,175 L 182,175 L 177,180 Z"))
        paths.append(format_path("tiger_stripe_r2", "M 238,175 L 218,175 L 223,180 Z"))
        paths.append(format_path("tiger_stripe_body_l", "M 130,240 L 170,240 L 160,250 Z"))
        paths.append(format_path("tiger_stripe_body_r", "M 270,240 L 230,240 L 240,250 Z"))
        paths.append(format_path("tiger_eye_l", "M 180,150 A 4,4 0 1,1 188,150 Z"))
        paths.append(format_path("tiger_eye_r", "M 212,150 A 4,4 0 1,1 220,150 Z"))
        paths.append(format_path("tiger_tail", "M 120,290 C 80,310 60,260 50,290 C 70,330 100,320 120,290 Z"))

    # 2. Elephant (Large Ears & Trunk)
    elif name == "Elephant":
        paths.append(format_path("ele_body", "M 100,220 C 100,170 300,170 300,220 L 290,320 L 110,320 Z"))
        paths.append(format_path("ele_head", "M 160,180 A 40,40 0 1,1 240,180 A 40,40 0 1,1 160,180 Z"))
        paths.append(format_path("ele_ear_l", "M 170,140 C 110,120 100,220 160,210 Z"))
        paths.append(format_path("ele_ear_r", "M 230,140 C 290,120 300,220 240,210 Z"))
        paths.append(format_path("ele_trunk", "M 200,195 Q 210,230 190,260 Q 170,280 185,290 Q 210,280 220,240 Q 215,210 200,195 Z"))
        paths.append(format_path("ele_tusk_l", "M 180,205 L 160,225 Q 175,225 185,215 Z"))
        paths.append(format_path("ele_tusk_r", "M 220,205 L 240,225 Q 225,225 215,215 Z"))
        paths.append(format_path("ele_leg_fl", "M 120,320 L 150,320 L 150,370 L 120,370 Z"))
        paths.append(format_path("ele_leg_fr", "M 165,320 L 195,320 L 195,370 L 165,370 Z"))
        paths.append(format_path("ele_leg_bl", "M 205,320 L 235,320 L 235,370 L 205,370 Z"))
        paths.append(format_path("ele_leg_br", "M 250,320 L 280,320 L 280,370 L 250,370 Z"))

    # 3. Wolf (Pointy Snout, Alert Ears)
    elif name == "Wolf":
        paths.append(format_path("wolf_body", "M 100,240 Q 180,210 250,250 L 230,330 L 120,330 Z"))
        paths.append(format_path("wolf_neck", "M 190,250 L 230,170 Q 220,150 170,200 Z"))
        paths.append(format_path("wolf_head", "M 200,160 L 260,140 L 230,120 Q 190,140 200,160 Z"))
        paths.append(format_path("wolf_ear_l", "M 205,125 L 195,85 L 218,118 Z"))
        paths.append(format_path("wolf_ear_r", "M 225,120 L 235,80 L 232,115 Z"))
        paths.append(format_path("wolf_snout", "M 250,143 L 285,148 L 255,158 Z"))
        paths.append(format_path("wolf_eye", "M 222,138 A 4,3 0 1,1 230,138 Z"))
        paths.append(format_path("wolf_tail", "M 100,260 Q 60,250 50,310 Q 80,320 100,260 Z"))

    # 4. Panda (Black Eye Circles & Ears)
    elif name == "Panda":
        paths.append(format_path("panda_body", "M 110,210 C 110,160 290,160 290,210 L 270,320 L 130,320 Z"))
        paths.append(format_path("panda_head", "M 160,160 A 40,40 0 1,1 240,160 A 40,40 0 1,1 160,160 Z"))
        paths.append(format_path("panda_ear_l", "M 165,130 A 15,15 0 1,0 145,150 Z"))
        paths.append(format_path("panda_ear_r", "M 235,130 A 15,15 0 1,1 255,150 Z"))
        paths.append(format_path("panda_eye_patch_l", "M 175,150 Q 185,145 190,160 Q 180,170 175,150 Z"))
        paths.append(format_path("panda_eye_patch_r", "M 225,150 Q 215,145 210,160 Q 220,170 225,150 Z"))
        paths.append(format_path("panda_eye_l", "M 182,152 A 2,2 0 1,1 186,152 Z"))
        paths.append(format_path("panda_eye_r", "M 214,152 A 2,2 0 1,1 218,152 Z"))
        paths.append(format_path("panda_snout", "M 190,170 L 210,170 L 200,180 Z"))

    # 5. Bear (Round Body & Snout)
    elif name == "Bear":
        paths.append(format_path("bear_body", "M 90,200 C 90,140 310,140 310,200 L 290,320 L 110,320 Z"))
        paths.append(format_path("bear_head", "M 160,150 A 40,40 0 1,1 240,150 A 40,40 0 1,1 160,150 Z"))
        paths.append(format_path("bear_ear_l", "M 170,120 A 12,12 0 1,0 150,135 Z"))
        paths.append(format_path("bear_ear_r", "M 230,120 A 12,12 0 1,1 250,135 Z"))
        paths.append(format_path("bear_snout", "M 180,165 Q 200,155 220,165 L 210,185 L 190,185 Z"))
        paths.append(format_path("bear_eye_l", "M 182,143 A 3,3 0 1,1 188,143 Z"))
        paths.append(format_path("bear_eye_r", "M 212,143 A 3,3 0 1,1 218,143 Z"))

    # 6. Lion (Thick Spiky Mane)
    elif name == "Lion":
        paths.append(format_path("lion_body", "M 110,230 Q 180,200 270,230 L 250,330 L 130,330 Z"))
        # Spiky Mane around head
        paths.append(format_path("lion_mane", "M 200,70 L 220,95 L 245,75 L 250,105 L 278,95 L 268,125 L 295,128 L 275,152 L 295,168 L 270,182 L 280,210 L 252,205 L 250,232 L 225,215 L 200,235 L 175,215 L 150,232 L 148,205 L 120,210 L 130,182 L 105,168 L 125,152 L 105,128 L 132,125 L 122,95 L 150,105 L 155,75 L 180,95 Z"))
        paths.append(format_path("lion_head", "M 160,150 A 40,40 0 1,1 240,150 A 40,40 0 1,1 160,150 Z"))
        paths.append(format_path("lion_snout", "M 185,170 L 215,170 Q 200,195 185,170 Z"))
        paths.append(format_path("lion_eye_l", "M 180,142 A 4,4 0 1,1 188,142 Z"))
        paths.append(format_path("lion_eye_r", "M 212,142 A 4,4 0 1,1 220,142 Z"))

    # 7. Fox (Pointy Snout & Bushy Tail)
    elif name == "Fox":
        paths.append(format_path("fox_body", "M 110,230 Q 170,200 230,230 L 215,310 L 125,310 Z"))
        paths.append(format_path("fox_head_back", "M 160,160 Q 200,120 240,160 L 230,190 Q 200,210 170,190 Z"))
        paths.append(format_path("fox_cheek_l", "M 160,160 Q 150,195 180,185 Z"))
        paths.append(format_path("fox_cheek_r", "M 240,160 Q 250,195 220,185 Z"))
        paths.append(format_path("fox_ear_l", "M 170,140 L 150,90 L 185,130 Z"))
        paths.append(format_path("fox_ear_r", "M 230,140 L 250,90 L 215,130 Z"))
        paths.append(format_path("fox_nose", "M 190,190 L 210,190 L 200,200 Z"))
        paths.append(format_path("fox_eye_l", "M 175,162 A 3,3 0 1,1 181,162 Z"))
        paths.append(format_path("fox_eye_r", "M 219,162 A 3,3 0 1,1 225,162 Z"))
        paths.append(format_path("fox_tail", "M 115,260 C 70,240 50,300 45,330 C 75,340 100,320 115,260 Z"))
        paths.append(format_path("fox_tail_tip", "M 45,330 Q 60,315 75,335 Z"))

    # 8. Deer (Slender Neck & Antlers)
    elif name == "Deer":
        paths.append(format_path("deer_body", "M 90,260 Q 160,230 230,260 L 210,340 L 110,340 Z"))
        paths.append(format_path("deer_neck", "M 180,260 L 210,160 L 165,190 Z"))
        paths.append(format_path("deer_head", "M 175,170 Q 215,130 225,170 L 195,195 Z"))
        paths.append(format_path("deer_ear_l", "M 180,155 L 140,145 Q 165,165 180,155 Z"))
        paths.append(format_path("deer_ear_r", "M 215,150 L 255,135 Q 235,160 215,150 Z"))
        paths.append(format_path("deer_antler_l", "M 190,145 L 180,95 L 160,85 M 180,95 L 195,80"))
        paths.append(format_path("deer_antler_r", "M 210,140 L 220,90 L 240,80 M 220,90 L 205,75"))
        paths.append(format_path("deer_eye", "M 195,165 A 3,3 0 1,1 201,165 Z"))

    # 9. Rabbit (Long Ears & Tiny Tail)
    elif name == "Rabbit":
        paths.append(format_path("rab_body", "M 120,240 C 120,190 260,190 260,240 L 245,320 L 135,320 Z"))
        paths.append(format_path("rab_head", "M 165,170 A 30,30 0 1,1 225,170 A 30,30 0 1,1 165,170 Z"))
        paths.append(format_path("rab_ear_l", "M 180,145 C 170,70 195,70 195,145 Z"))
        paths.append(format_path("rab_ear_r", "M 210,145 C 220,70 195,70 195,145 Z"))
        paths.append(format_path("rab_snout", "M 190,185 L 210,185 L 200,195 Z"))
        paths.append(format_path("rab_eye_l", "M 180,165 A 3,3 0 1,1 186,165 Z"))
        paths.append(format_path("rab_eye_r", "M 214,165 A 3,3 0 1,1 220,165 Z"))
        paths.append(format_path("rab_tail", "M 255,270 A 15,15 0 1,1 255,300 Z"))

    # 10. Squirrel (Big Curved Bushy Tail)
    elif name == "Squirrel":
        paths.append(format_path("sq_body", "M 120,240 C 120,200 240,200 240,240 L 220,320 L 140,320 Z"))
        paths.append(format_path("sq_head", "M 155,180 A 25,25 0 1,1 205,180 A 25,25 0 1,1 155,180 Z"))
        paths.append(format_path("sq_ear_l", "M 165,160 L 160,130 L 175,158 Z"))
        paths.append(format_path("sq_ear_r", "M 195,160 L 200,130 L 185,158 Z"))
        paths.append(format_path("sq_snout", "M 175,190 L 185,190 L 180,196 Z"))
        paths.append(format_path("sq_eye", "M 185,178 A 3,3 0 1,1 191,178 Z"))
        paths.append(format_path("sq_tail", "M 230,280 C 290,260 300,140 270,120 C 240,100 240,160 215,220 Z"))

    # 11. Owl (Big Eyes, Branch)
    elif name == "Owl":
        paths.append(format_path("owl_body", "M 140,150 C 140,110 260,110 260,150 L 240,280 Q 200,300 160,280 Z"))
        paths.append(format_path("owl_eye_l_out", "M 150,160 A 22,22 0 1,1 194,160 A 22,22 0 1,1 150,160 Z"))
        paths.append(format_path("owl_eye_r_out", "M 206,160 A 22,22 0 1,1 250,160 A 22,22 0 1,1 206,160 Z"))
        paths.append(format_path("owl_pupil_l", "M 164,160 A 8,8 0 1,1 180,160 Z"))
        paths.append(format_path("owl_pupil_r", "M 220,160 A 8,8 0 1,1 236,160 Z"))
        paths.append(format_path("owl_beak", "M 195,175 L 205,175 L 200,195 Z"))
        paths.append(format_path("owl_wing_l", "M 143,180 Q 110,210 150,260 Z"))
        paths.append(format_path("owl_wing_r", "M 257,180 Q 290,210 250,260 Z"))
        paths.append(format_path("owl_tummy", "M 170,210 Q 200,195 230,210 Q 200,280 170,210 Z"))
        paths.append(format_path("owl_branch", "M 80,310 L 320,310 L 320,320 L 80,320 Z"))

    # 12. Eagle (Outstretched Wings, Sharp Beak)
    elif name == "Eagle":
        paths.append(format_path("eagle_head", "M 170,150 Q 200,110 230,150 L 220,180 L 180,180 Z"))
        paths.append(format_path("eagle_beak", "M 195,155 L 215,160 Q 200,180 190,170 Z"))
        paths.append(format_path("eagle_body", "M 160,180 Q 200,170 240,180 L 220,270 Q 200,290 180,270 Z"))
        paths.append(format_path("eagle_wing_l", "M 160,190 L 50,120 L 120,230 L 170,210 Z"))
        paths.append(format_path("eagle_wing_r", "M 240,190 L 350,120 L 280,230 L 230,210 Z"))
        paths.append(format_path("eagle_tail", "M 185,270 L 170,320 L 230,320 L 215,270 Z"))

    # 13. Falcon (Hunting Pose, Sharp Eye)
    elif name == "Falcon":
        paths.append(format_path("fal_head", "M 180,140 Q 200,110 220,135 L 210,165 L 185,165 Z"))
        paths.append(format_path("fal_beak", "M 205,142 L 225,145 Q 215,160 205,155 Z"))
        paths.append(format_path("fal_body", "M 175,165 C 160,190 180,270 210,270 C 230,270 235,190 220,165 Z"))
        paths.append(format_path("fal_wing_l", "M 175,175 Q 110,180 145,260 Z"))
        paths.append(format_path("fal_wing_r", "M 220,175 Q 285,180 250,260 Z"))
        paths.append(format_path("fal_eye", "M 193,142 A 2.5,2.5 0 1,1 198,142 Z"))

    # 14. Parrot (Tropical Curved Beak & Tail)
    elif name == "Parrot":
        paths.append(format_path("par_head", "M 170,130 A 25,25 0 1,1 220,130 A 25,25 0 1,1 170,130 Z"))
        paths.append(format_path("par_beak", "M 205,125 Q 230,130 215,155 Q 205,145 205,135 Z"))
        paths.append(format_path("par_body", "M 175,155 C 160,180 160,250 190,260 C 220,250 215,180 205,155 Z"))
        paths.append(format_path("par_wing", "M 190,165 L 165,225 L 195,245 Z"))
        paths.append(format_path("par_tail", "M 185,260 L 170,350 L 205,330 L 195,260 Z"))
        paths.append(format_path("par_branch", "M 120,280 L 280,280 L 280,290 L 120,290 Z"))

    # 15. Penguin (Tuxedo Body, Flippers)
    elif name == "Penguin":
        paths.append(format_path("pen_body", "M 150,160 C 130,200 130,290 160,310 C 190,320 220,310 240,290 C 270,270 250,180 230,160 Z"))
        paths.append(format_path("pen_tuxedo", "M 175,180 Q 200,160 225,180 Q 235,250 215,295 Q 185,295 175,180 Z"))
        paths.append(format_path("pen_flipper_l", "M 148,200 C 120,220 120,260 148,270 Z"))
        paths.append(format_path("pen_flipper_r", "M 232,200 C 260,220 260,260 232,270 Z"))
        paths.append(format_path("pen_beak", "M 190,170 L 210,170 L 200,185 Z"))
        paths.append(format_path("pen_foot_l", "M 160,310 L 140,330 L 175,330 Z"))
        paths.append(format_path("pen_foot_r", "M 220,310 L 245,330 L 210,330 Z"))

    # 16. Dolphin (Jumping, Waves)
    elif name == "Dolphin":
        paths.append(format_path("dol_body", "M 80,220 C 100,130 250,110 320,180 C 335,195 320,210 290,210 C 210,210 140,270 80,220 Z"))
        paths.append(format_path("dol_snout", "M 320,180 L 335,178 L 320,195 Z"))
        paths.append(format_path("dol_fin_top", "M 190,138 Q 205,100 225,130 Z"))
        paths.append(format_path("dol_flipper", "M 180,205 Q 165,235 195,225 Z"))
        paths.append(format_path("dol_tail_u", "M 85,220 L 55,190 L 68,225 Z"))
        paths.append(format_path("dol_tail_d", "M 85,220 L 55,250 L 68,215 Z"))
        paths.append(format_path("dol_waves", "M 40,260 Q 120,230 200,260 Q 280,290 360,260 L 360,300 L 40,300 Z"))

    # 17. Whale (Huge Tail & Blowhole)
    elif name == "Whale":
        paths.append(format_path("wh_body", "M 70,220 C 110,140 280,120 330,190 C 350,215 310,250 250,250 C 180,250 120,270 70,220 Z"))
        paths.append(format_path("wh_tail_t", "M 73,220 L 35,180 L 50,220 Z"))
        paths.append(format_path("wh_tail_b", "M 73,220 L 35,260 L 50,220 Z"))
        paths.append(format_path("wh_flipper", "M 190,230 Q 170,280 205,260 Z"))
        paths.append(format_path("wh_spout_l", "M 260,140 Q 250,110 240,120 Q 255,130 260,140 Z"))
        paths.append(format_path("wh_spout_r", "M 265,140 Q 275,110 285,120 Q 270,130 265,140 Z"))
        paths.append(format_path("wh_belly_lines", "M 250,220 Q 200,230 150,220"))
        paths.append(format_path("wh_eye", "M 305,190 A 3,3 0 1,1 311,190 Z"))

    # 18. Shark (Sharp Teeth & Fins)
    elif name == "Shark":
        paths.append(format_path("sh_body", "M 70,220 C 120,130 270,130 330,200 C 300,230 250,240 70,220 Z"))
        paths.append(format_path("sh_mouth", "M 280,210 Q 295,225 315,208 Z"))
        paths.append(format_path("sh_teeth", "M 285,212 L 290,218 L 295,212 L 300,218 L 305,212"))
        paths.append(format_path("sh_fin_top", "M 180,148 Q 200,90 220,140 Z"))
        paths.append(format_path("sh_fin_bot", "M 180,228 Q 170,265 195,232 Z"))
        paths.append(format_path("sh_tail_u", "M 75,220 L 40,160 L 60,210 Z"))
        paths.append(format_path("sh_tail_d", "M 75,220 L 40,260 L 60,220 Z"))
        paths.append(format_path("sh_eye", "M 300,175 A 4,4 0 1,1 308,175 Z"))

    # 19. Octopus (Head & Wavy Tentacles)
    elif name == "Octopus":
        paths.append(format_path("oct_head", "M 140,180 C 140,110 260,110 260,180 C 260,220 140,220 140,180 Z"))
        paths.append(format_path("oct_eye_l", "M 170,180 A 6,6 0 1,1 182,180 Z"))
        paths.append(format_path("oct_eye_r", "M 218,180 A 6,6 0 1,1 230,180 Z"))
        paths.append(format_path("oct_mouth", "M 195,195 Q 200,205 205,195 Z"))
        paths.append(format_path("oct_tentacle_1", "M 150,210 Q 110,220 90,250 Q 110,270 150,220 Z"))
        paths.append(format_path("oct_tentacle_2", "M 170,215 Q 140,250 120,300 Q 145,310 180,220 Z"))
        paths.append(format_path("oct_tentacle_3", "M 200,218 Q 200,270 200,320 Q 215,320 215,218 Z"))
        paths.append(format_path("oct_tentacle_4", "M 230,215 Q 260,250 280,300 Q 255,310 220,220 Z"))
        paths.append(format_path("oct_tentacle_5", "M 250,210 Q 290,220 310,250 Q 290,270 250,220 Z"))

    # 20. Seahorse (Curled Tail & Crown)
    elif name == "Seahorse":
        paths.append(format_path("sh_head", "M 170,120 Q 190,95 210,120 Q 200,140 180,140 Z"))
        paths.append(format_path("sh_snout", "M 200,120 L 230,125 L 205,135 Z"))
        paths.append(format_path("sh_crown", "M 180,105 L 188,90 L 192,103 Z"))
        paths.append(format_path("sh_body", "M 180,140 Q 150,180 180,240 Q 200,280 180,310 Q 160,330 180,340 Q 205,330 190,300 Q 215,250 195,190 Z"))
        paths.append(format_path("sh_fin_back", "M 168,180 Q 150,195 168,210 Z"))
        paths.append(format_path("sh_eye", "M 185,120 A 3,3 0 1,1 191,120 Z"))

    # 21. Frog (Wide Mouth, Lily Pad)
    elif name == "Frog":
        paths.append(format_path("frog_body", "M 130,220 Q 200,170 270,220 Q 270,285 200,290 Q 130,285 130,220 Z"))
        paths.append(format_path("frog_eye_l", "M 150,185 A 15,15 0 1,1 180,185 Z"))
        paths.append(format_path("frog_eye_r", "M 220,185 A 15,15 0 1,1 250,185 Z"))
        paths.append(format_path("frog_pupil_l", "M 160,185 A 4,4 0 1,1 168,185 Z"))
        paths.append(format_path("frog_pupil_r", "M 230,185 A 4,4 0 1,1 238,185 Z"))
        paths.append(format_path("frog_mouth", "M 160,225 Q 200,250 240,225 Z"))
        paths.append(format_path("frog_leg_l", "M 130,240 Q 90,260 120,300 Z"))
        paths.append(format_path("frog_leg_r", "M 270,240 Q 310,260 280,300 Z"))
        paths.append(format_path("lily_pad", "M 80,310 Q 200,280 320,310 L 290,330 Q 200,320 110,330 Z"))

    # 22. Chameleon (Spiral Tail)
    elif name == "Chameleon":
        paths.append(format_path("cham_body", "M 130,200 Q 200,140 250,200 L 230,250 L 150,250 Z"))
        paths.append(format_path("cham_head", "M 120,195 Q 85,160 135,160 Z"))
        paths.append(format_path("cham_eye", "M 115,178 A 10,10 0 1,1 135,178 Z"))
        paths.append(format_path("cham_pupil", "M 123,178 A 2,2 0 1,1 127,178 Z"))
        paths.append(format_path("cham_spiral_tail", "M 245,210 C 290,190 310,270 260,280 C 230,285 240,250 250,245 Z"))
        paths.append(format_path("cham_branch", "M 60,260 L 340,260 L 340,275 L 60,275 Z"))

    # 23. Snake (Coiled S-Shape)
    elif name == "Snake":
        paths.append(format_path("snake_head", "M 200,120 Q 220,100 240,120 L 230,140 L 210,140 Z"))
        paths.append(format_path("snake_eye", "M 222,122 A 2.5,2.5 0 1,1 227,122 Z"))
        paths.append(format_path("snake_tongue", "M 235,130 L 255,133 L 250,138 L 235,130 Z"))
        paths.append(format_path("snake_coil_1", "M 220,140 Q 240,180 180,200 Q 120,220 160,250 Q 200,280 260,250 Q 300,230 260,200 Z"))
        paths.append(format_path("snake_coil_2", "M 260,250 Q 280,290 200,320 Q 120,290 140,260 Z"))

    # 24. Crocodile (Spiky Back & Open Jaw)
    elif name == "Crocodile":
        paths.append(format_path("croc_body", "M 110,240 Q 200,190 290,240 L 270,300 L 130,300 Z"))
        paths.append(format_path("croc_jaw_u", "M 120,210 L 60,200 L 120,235 Z"))
        paths.append(format_path("croc_jaw_d", "M 120,235 L 65,245 L 120,255 Z"))
        paths.append(format_path("croc_teeth", "M 70,203 L 75,208 L 80,203 L 85,208 L 90,203 L 95,208 M 70,242 L 75,237 L 80,242 L 85,237 L 90,242"))
        paths.append(format_path("croc_spikes", "M 170,205 L 180,190 L 190,203 L 200,190 L 210,202 L 220,190 L 230,203 L 240,190 L 250,205"))
        paths.append(format_path("croc_eye", "M 130,202 A 4,4 0 1,1 138,202 Z"))
        paths.append(format_path("croc_tail", "M 290,250 Q 350,230 360,290 Q 320,300 290,270 Z"))

    # 25. Butterfly (Symmetric Wings)
    elif name == "Butterfly":
        paths.append(format_path("bf_body", "M 195,140 L 205,140 L 205,280 L 195,280 Z"))
        paths.append(format_path("bf_head", "M 190,130 A 10,10 0 1,1 210,130 Z"))
        paths.append(format_path("bf_wing_ul", "M 195,160 C 130,90 90,170 195,210 Z"))
        paths.append(format_path("bf_wing_ur", "M 205,160 C 270,90 310,170 205,210 Z"))
        paths.append(format_path("bf_wing_dl", "M 195,220 C 120,240 150,300 195,270 Z"))
        paths.append(format_path("bf_wing_dr", "M 205,220 C 280,240 250,300 205,270 Z"))
        paths.append(format_path("bf_pattern_ul", "M 150,150 A 15,15 0 1,1 175,170 Z"))
        paths.append(format_path("bf_pattern_ur", "M 250,150 A 15,15 0 1,1 225,170 Z"))

    # 26. Dragonfly (Double Long Wings)
    elif name == "Dragonfly":
        paths.append(format_path("df_body", "M 197,120 L 203,120 L 203,340 L 197,340 Z"))
        paths.append(format_path("df_head", "M 190,110 A 10,10 0 1,1 210,110 Z"))
        paths.append(format_path("df_wing_ul", "M 197,140 C 90,120 70,170 197,170 Z"))
        paths.append(format_path("df_wing_ur", "M 203,140 C 310,120 330,170 203,170 Z"))
        paths.append(format_path("df_wing_dl", "M 197,175 C 100,170 80,210 197,200 Z"))
        paths.append(format_path("df_wing_dr", "M 203,175 C 300,170 320,210 203,200 Z"))

    # 27. Ladybug (Spots, Round)
    elif name == "Ladybug":
        paths.append(format_path("lb_body", "M 140,220 A 60,60 0 1,1 260,220 A 60,60 0 1,1 140,220 Z"))
        paths.append(format_path("lb_head", "M 175,165 A 25,25 0 1,1 225,165 Z"))
        paths.append(format_path("lb_divider", "M 200,165 L 200,280"))
        paths.append(format_path("lb_spot_l1", "M 160,200 A 8,8 0 1,1 176,200 Z"))
        paths.append(format_path("lb_spot_l2", "M 165,240 A 8,8 0 1,1 181,240 Z"))
        paths.append(format_path("lb_spot_r1", "M 224,200 A 8,8 0 1,1 240,200 Z"))
        paths.append(format_path("lb_spot_r2", "M 219,240 A 8,8 0 1,1 235,240 Z"))

    # 28. Bee (Stripes & Stinger)
    elif name == "Bee":
        paths.append(format_path("bee_body", "M 140,200 C 140,160 260,160 260,200 C 260,250 140,250 140,200 Z"))
        paths.append(format_path("bee_stripe1", "M 170,170 L 170,230 L 185,230 L 185,170 Z"))
        paths.append(format_path("bee_stripe2", "M 210,170 L 210,230 L 225,230 L 225,170 Z"))
        paths.append(format_path("bee_wing_l", "M 180,170 C 150,110 190,90 200,170 Z"))
        paths.append(format_path("bee_wing_r", "M 220,170 C 250,110 210,90 200,170 Z"))
        paths.append(format_path("bee_stinger", "M 260,200 L 285,200 L 260,210 Z"))
        paths.append(format_path("bee_head", "M 120,180 A 20,20 0 1,1 140,210 Z"))

    # 29. Cat (Whiskers, Sitting)
    elif name == "Cat":
        paths.append(format_path("cat_body", "M 130,240 C 130,195 270,195 270,240 L 250,330 L 150,330 Z"))
        paths.append(format_path("cat_head", "M 160,160 A 40,40 0 1,1 240,160 A 40,40 0 1,1 160,160 Z"))
        paths.append(format_path("cat_ear_l", "M 170,130 L 150,90 L 185,122 Z"))
        paths.append(format_path("cat_ear_r", "M 230,130 L 250,90 L 215,122 Z"))
        paths.append(format_path("cat_snout", "M 192,180 L 208,180 L 200,188 Z"))
        paths.append(format_path("cat_whisker_l1", "M 175,183 L 145,180"))
        paths.append(format_path("cat_whisker_l2", "M 175,188 L 140,193"))
        paths.append(format_path("cat_whisker_r1", "M 225,183 L 255,180"))
        paths.append(format_path("cat_whisker_r2", "M 225,188 L 260,193"))
        paths.append(format_path("cat_eye_l", "M 182,155 A 4,4 0 1,1 190,155 Z"))
        paths.append(format_path("cat_eye_r", "M 210,155 A 4,4 0 1,1 218,155 Z"))
        paths.append(format_path("cat_tail", "M 260,290 C 310,290 320,200 300,230 Z"))

    # 30. Dog (Floppy Ears & Collar)
    elif name == "Dog":
        paths.append(format_path("dog_body", "M 120,240 C 120,200 280,200 280,240 L 260,330 L 140,330 Z"))
        paths.append(format_path("dog_head", "M 160,160 A 40,40 0 1,1 240,160 A 40,40 0 1,1 160,160 Z"))
        paths.append(format_path("dog_ear_l", "M 165,135 C 130,130 130,200 165,190 Z"))
        paths.append(format_path("dog_ear_r", "M 235,135 C 270,130 270,200 235,190 Z"))
        paths.append(format_path("dog_snout", "M 180,180 Q 200,168 220,180 L 210,205 L 190,205 Z"))
        paths.append(format_path("dog_collar", "M 155,235 L 245,235 L 240,245 L 160,245 Z"))
        paths.append(format_path("dog_eye_l", "M 182,150 A 4,4 0 1,1 190,150 Z"))
        paths.append(format_path("dog_eye_r", "M 210,150 A 4,4 0 1,1 218,150 Z"))

    # 31. Horse (Flowing Mane)
    elif name == "Horse":
        paths.append(format_path("horse_body", "M 90,250 Q 160,220 230,250 L 210,340 L 110,340 Z"))
        paths.append(format_path("horse_neck", "M 180,250 L 215,160 L 160,190 Z"))
        paths.append(format_path("horse_head", "M 170,170 L 220,120 L 240,140 L 190,190 Z"))
        paths.append(format_path("horse_ear_l", "M 180,150 L 175,115 L 190,140 Z"))
        paths.append(format_path("horse_ear_r", "M 205,135 L 208,100 L 218,125 Z"))
        paths.append(format_path("horse_mane", "M 170,190 C 145,200 150,230 180,240 M 180,180 C 155,190 160,210 185,220"))

    # 32. Cow (Horns & Spots)
    elif name == "Cow":
        paths.append(format_path("cow_body", "M 90,220 C 90,160 310,160 310,220 L 290,320 L 110,320 Z"))
        paths.append(format_path("cow_head", "M 160,150 A 40,40 0 1,1 240,150 A 40,40 0 1,1 160,150 Z"))
        paths.append(format_path("cow_horn_l", "M 165,120 Q 145,95 160,105 Z"))
        paths.append(format_path("cow_horn_r", "M 235,120 Q 255,95 240,105 Z"))
        paths.append(format_path("cow_spot1", "M 110,210 Q 140,200 130,240 Q 100,230 110,210 Z"))
        paths.append(format_path("cow_spot2", "M 260,220 Q 285,195 290,240 Q 265,260 260,220 Z"))
        paths.append(format_path("cow_snout", "M 170,165 Q 200,155 230,165 L 220,185 L 180,185 Z"))

    # 33. Sheep (Fluffy Cloud Body)
    elif name == "Sheep":
        paths.append(format_path("sheep_body", "M 140,190 C 120,190 100,210 100,230 C 100,250 120,270 140,270 C 130,290 150,310 170,310 C 190,310 200,295 210,310 C 230,310 250,290 250,270 C 270,270 290,250 290,230 C 290,210 270,190 250,190 C 250,170 230,150 210,150 C 190,150 170,150 150,150 C 145,170 140,180 140,190 Z"))
        paths.append(format_path("sheep_head", "M 170,170 A 25,25 0 1,1 220,170 A 25,25 0 1,1 170,170 Z"))
        paths.append(format_path("sheep_ear_l", "M 175,160 Q 150,160 168,175 Z"))
        paths.append(format_path("sheep_ear_r", "M 215,160 Q 240,160 222,175 Z"))
        paths.append(format_path("sheep_leg_fl", "M 130,270 L 130,330 L 145,330 L 145,270 Z"))
        paths.append(format_path("sheep_leg_br", "M 220,270 L 220,330 L 235,330 L 235,270 Z"))

    # 34. Pig (Snout & Curly Tail)
    elif name == "Pig":
        paths.append(format_path("pig_body", "M 100,210 C 100,160 300,160 300,210 L 280,320 L 120,320 Z"))
        paths.append(format_path("pig_head", "M 160,160 A 40,40 0 1,1 240,160 Z"))
        paths.append(format_path("pig_ear_l", "M 165,135 L 145,105 L 175,125 Z"))
        paths.append(format_path("pig_ear_r", "M 235,135 L 255,105 L 225,125 Z"))
        paths.append(format_path("pig_snout", "M 185,175 L 215,175 A 15,15 0 1,1 185,175 Z"))
        paths.append(format_path("pig_nostril_l", "M 193,185 A 2,2 0 1,1 197,185 Z"))
        paths.append(format_path("pig_nostril_r", "M 203,185 A 2,2 0 1,1 207,185 Z"))
        paths.append(format_path("pig_tail", "M 290,260 Q 320,250 310,275 Q 300,280 290,270 Z"))

    # 35. Goat (Horns & Beard)
    elif name == "Goat":
        paths.append(format_path("goat_body", "M 90,240 Q 160,215 230,240 L 210,330 L 110,330 Z"))
        paths.append(format_path("goat_head", "M 170,160 L 210,120 L 230,150 L 190,190 Z"))
        paths.append(format_path("goat_ear_l", "M 175,145 Q 145,140 165,155 Z"))
        paths.append(format_path("goat_ear_r", "M 210,135 Q 240,130 220,145 Z"))
        paths.append(format_path("goat_horn_l", "M 185,125 Q 170,80 185,90 Z"))
        paths.append(format_path("goat_horn_r", "M 205,115 Q 190,70 205,80 Z"))
        paths.append(format_path("goat_beard", "M 180,185 L 185,215 L 195,190 Z"))

    # 36. Chicken (Comb & Beak)
    elif name == "Chicken":
        paths.append(format_path("chk_body", "M 140,190 C 140,150 260,150 260,190 L 240,280 Q 200,300 160,280 Z"))
        paths.append(format_path("chk_head", "M 170,130 A 25,25 0 1,1 220,130 Z"))
        paths.append(format_path("chk_comb", "M 180,108 Q 185,90 195,100 Q 200,90 205,100 Q 210,92 215,108 Z"))
        paths.append(format_path("chk_beak", "M 215,125 L 230,130 L 215,138 Z"))
        paths.append(format_path("chk_wattle", "M 210,138 Q 215,150 205,145 Z"))
        paths.append(format_path("chk_wing", "M 160,200 Q 190,180 220,225 L 170,250 Z"))

    # 37. Duck (Swimming bill & waves)
    elif name == "Duck":
        paths.append(format_path("dck_body", "M 120,230 C 120,190 250,190 250,230 L 230,280 Q 180,290 130,280 Z"))
        paths.append(format_path("dck_head", "M 140,170 A 25,25 0 1,1 190,170 Z"))
        paths.append(format_path("dck_bill", "M 180,165 L 210,170 Q 195,185 180,178 Z"))
        paths.append(format_path("dck_wing", "M 140,225 Q 175,200 210,240 L 160,260 Z"))
        paths.append(format_path("dck_waves", "M 60,290 Q 140,270 220,290 Q 300,310 380,290 L 380,315 L 60,315 Z"))

    # 38. T-Rex (Dinosaur Jaws)
    elif name == "T-Rex":
        paths.append(format_path("dino_body", "M 130,220 C 130,170 300,160 300,240 Q 290,320 220,330 L 150,330 Z"))
        paths.append(format_path("dino_head", "M 140,150 L 90,140 L 95,190 L 150,190 Z"))
        paths.append(format_path("dino_jaw_open", "M 95,190 L 80,220 L 125,205 Z"))
        paths.append(format_path("dino_teeth", "M 92,152 L 95,160 L 100,152 L 105,160 L 110,152"))
        paths.append(format_path("dino_arm", "M 160,210 Q 140,210 145,225 L 160,225 Z"))
        paths.append(format_path("dino_tail", "M 300,230 Q 370,220 380,290 Q 330,300 290,260 Z"))

    # 39. Triceratops (Neck Frill, 3 Horns)
    elif name == "Triceratops":
        paths.append(format_path("tri_body", "M 110,230 Q 200,180 290,230 L 270,325 L 130,325 Z"))
        paths.append(format_path("tri_frill", "M 135,170 A 50,50 0 0,1 225,170 L 180,210 Z"))
        paths.append(format_path("tri_head", "M 145,190 L 110,210 L 140,235 Z"))
        paths.append(format_path("tri_horn_l", "M 130,185 L 90,170 L 125,195 Z"))
        paths.append(format_path("tri_horn_r", "M 160,185 L 120,170 L 155,195 Z"))
        paths.append(format_path("tri_horn_nose", "M 112,205 L 95,208 L 115,215 Z"))

    # 40. Stegosaurus (Back Plates & Spiked Tail)
    elif name == "Stegosaurus":
        paths.append(format_path("stg_body", "M 110,240 Q 200,180 290,240 L 270,320 L 130,320 Z"))
        paths.append(format_path("stg_head", "M 110,245 L 75,255 L 85,270 L 120,265 Z"))
        # Diamond back plates
        paths.append(format_path("stg_plate1", "M 135,210 L 145,180 L 155,210 Z"))
        paths.append(format_path("stg_plate2", "M 175,195 L 190,165 L 205,195 Z"))
        paths.append(format_path("stg_plate3", "M 225,195 L 240,165 L 255,195 Z"))
        paths.append(format_path("stg_plate4", "M 270,215 L 280,185 L 290,215 Z"))
        paths.append(format_path("stg_tail", "M 290,240 Q 350,240 360,290 L 320,300 Z"))
        paths.append(format_path("stg_spikes", "M 360,290 L 385,280 M 358,295 L 380,305"))

    # 41. Pterodactyl (Wings & Crest)
    elif name == "Pterodactyl":
        paths.append(format_path("pt_body", "M 175,180 L 225,180 L 210,270 L 190,270 Z"))
        paths.append(format_path("pt_head", "M 180,140 Q 200,120 220,140 M 175,140 L 140,135 M 225,140 L 260,148"))
        paths.append(format_path("pt_wing_l", "M 175,180 L 70,160 L 140,240 Z"))
        paths.append(format_path("pt_wing_r", "M 225,180 L 330,160 L 260,240 Z"))

    # 42. Dragon (Flame & Wings)
    elif name == "Dragon":
        paths.append(format_path("drag_body", "M 120,230 Q 200,180 280,230 L 260,320 L 140,320 Z"))
        paths.append(format_path("drag_head", "M 160,150 L 115,140 L 125,185 L 180,185 Z"))
        paths.append(format_path("drag_horn_l", "M 165,130 L 150,100 L 172,125 Z"))
        paths.append(format_path("drag_horn_r", "M 185,130 L 175,100 L 192,125 Z"))
        paths.append(format_path("drag_wing_l", "M 140,210 Q 70,140 100,260 Z"))
        paths.append(format_path("drag_wing_r", "M 260,210 Q 330,140 300,260 Z"))
        paths.append(format_path("drag_flame", "M 115,155 L 75,145 L 85,160 L 50,165 Q 80,180 115,170 Z"))

    # 43. Unicorn (Spiral Horn & Mane)
    elif name == "Unicorn":
        paths.append(format_path("uni_body", "M 90,250 Q 160,220 230,250 L 210,340 L 110,340 Z"))
        paths.append(format_path("uni_neck", "M 180,250 L 215,160 L 160,190 Z"))
        paths.append(format_path("uni_head", "M 170,170 L 220,120 L 240,140 L 190,190 Z"))
        paths.append(format_path("uni_horn", "M 205,128 L 225,70 L 218,122 Z"))
        paths.append(format_path("uni_mane", "M 170,190 C 145,200 150,230 180,240 M 180,180 C 155,190 160,210 185,220"))

    # 44. Pegasus (Wings & Horse Body)
    elif name == "Pegasus":
        paths.append(format_path("peg_body", "M 90,250 Q 160,220 230,250 L 210,340 L 110,340 Z"))
        paths.append(format_path("peg_neck", "M 180,250 L 215,160 L 160,190 Z"))
        paths.append(format_path("peg_head", "M 170,170 L 220,120 L 240,140 L 190,190 Z"))
        paths.append(format_path("peg_wing_l", "M 150,210 Q 60,120 120,240 Z"))
        paths.append(format_path("peg_wing_r", "M 220,210 Q 310,120 250,240 Z"))

    # 45. Phoenix (Flaming Wings)
    elif name == "Phoenix":
        paths.append(format_path("phx_head", "M 175,140 Q 200,110 225,140 Z"))
        paths.append(format_path("phx_crest", "M 195,115 L 200,90 L 205,115 Z"))
        paths.append(format_path("phx_body", "M 170,170 Q 200,150 230,170 L 210,260 Q 200,285 190,260 Z"))
        paths.append(format_path("phx_wing_l", "M 170,180 L 50,130 Q 110,230 155,220 Z"))
        paths.append(format_path("phx_wing_r", "M 230,180 L 350,130 Q 290,230 245,220 Z"))
        paths.append(format_path("phx_tail_f1", "M 195,270 L 175,350 L 200,310 Z"))
        paths.append(format_path("phx_tail_f2", "M 205,270 L 225,350 L 200,310 Z"))

    # 46. Monkey (Round Ears & Long Tail)
    elif name == "Monkey":
        paths.append(format_path("mon_body", "M 130,230 C 130,190 270,190 270,230 L 250,330 L 150,330 Z"))
        paths.append(format_path("mon_head", "M 160,150 A 40,40 0 1,1 240,150 Z"))
        paths.append(format_path("mon_ear_l", "M 160,150 A 15,15 0 1,0 135,165 Z"))
        paths.append(format_path("mon_ear_r", "M 240,150 A 15,15 0 1,1 265,165 Z"))
        paths.append(format_path("mon_face", "M 175,145 C 160,165 170,185 200,185 C 230,185 240,165 225,145 Z"))
        paths.append(format_path("mon_tail", "M 260,280 C 310,290 320,180 300,220 Z"))

    # 47. Gorilla (Broad Shoulders)
    elif name == "Gorilla":
        paths.append(format_path("gor_body", "M 100,210 C 100,140 300,140 300,210 L 280,330 L 120,330 Z"))
        paths.append(format_path("gor_head", "M 155,130 A 45,45 0 0,1 245,130 L 225,180 L 175,180 Z"))
        paths.append(format_path("gor_face", "M 170,140 L 230,140 L 220,175 L 180,175 Z"))
        paths.append(format_path("gor_chest_l", "M 140,220 Q 170,220 160,280 Z"))
        paths.append(format_path("gor_chest_r", "M 260,220 Q 230,220 240,280 Z"))

    # 48. Chimpanzee (Large Ears)
    elif name == "Chimpanzee":
        paths.append(format_path("chp_body", "M 120,230 C 120,185 280,185 280,230 L 260,330 L 140,330 Z"))
        paths.append(format_path("chp_head", "M 160,150 A 40,40 0 1,1 240,150 Z"))
        paths.append(format_path("chp_ear_l", "M 160,150 A 18,18 0 1,0 130,160 Z"))
        paths.append(format_path("chp_ear_r", "M 240,150 A 18,18 0 1,1 270,160 Z"))
        paths.append(format_path("chp_face", "M 170,150 Q 200,175 230,150 Q 220,190 180,190 Z"))

    # 49. Lemur (Long Striped Tail)
    elif name == "Lemur":
        paths.append(format_path("lem_body", "M 120,240 C 120,200 240,200 240,240 L 220,320 L 140,320 Z"))
        paths.append(format_path("lem_head", "M 160,180 A 25,25 0 1,1 210,180 Z"))
        paths.append(format_path("lem_ear_l", "M 165,162 L 150,135 L 175,160 Z"))
        paths.append(format_path("lem_ear_r", "M 205,162 L 220,135 L 195,160 Z"))
        paths.append(format_path("lem_eye_l", "M 172,175 A 5,5 0 1,1 182,175 Z"))
        paths.append(format_path("lem_eye_r", "M 188,175 A 5,5 0 1,1 198,175 Z"))
        paths.append(format_path("lem_tail", "M 230,270 Q 300,260 300,120 L 285,120 Q 285,250 220,285 Z"))
        paths.append(format_path("lem_tail_stripe1", "M 290,200 L 297,215"))
        paths.append(format_path("lem_tail_stripe2", "M 288,160 L 295,175"))

    # 50. Sloth (Branch, Hanging)
    elif name == "Sloth":
        paths.append(format_path("sl_branch", "M 60,160 L 340,160 L 340,175 L 60,175 Z"))
        paths.append(format_path("sl_body", "M 130,175 C 130,260 270,260 270,175 Z"))
        paths.append(format_path("sl_arm_l", "M 140,175 L 140,160 M 150,175 L 150,160"))
        paths.append(format_path("sl_arm_r", "M 250,175 L 250,160 M 260,175 L 260,160"))
        paths.append(format_path("sl_head", "M 180,210 A 25,25 0 1,1 230,210 Z"))
        paths.append(format_path("sl_eye_strip", "M 190,205 L 220,205"))

    # 51. Koala (Large Round Fuzzy Ears)
    elif name == "Koala":
        paths.append(format_path("ko_body", "M 120,230 C 120,180 280,180 280,230 L 260,330 L 140,330 Z"))
        paths.append(format_path("ko_head", "M 160,160 A 40,40 0 1,1 240,160 Z"))
        paths.append(format_path("ko_ear_l", "M 160,150 A 20,20 0 1,0 120,170 Z"))
        paths.append(format_path("ko_ear_r", "M 240,150 A 20,20 0 1,1 280,170 Z"))
        paths.append(format_path("ko_nose", "M 190,170 Q 200,160 210,170 L 205,195 L 195,195 Z"))
        paths.append(format_path("ko_eye_l", "M 178,155 A 3,3 0 1,1 184,155 Z"))
        paths.append(format_path("ko_eye_r", "M 216,155 A 3,3 0 1,1 222,155 Z"))

    # 52. Kangaroo (Large Legs & Pouch)
    elif name == "Kangaroo":
        paths.append(format_path("kan_body", "M 90,250 C 90,200 230,180 230,250 L 210,335 L 110,335 Z"))
        paths.append(format_path("kan_head", "M 170,160 L 210,120 L 225,135 L 190,180 Z"))
        paths.append(format_path("kan_ear_l", "M 180,140 L 170,95 L 188,130 Z"))
        paths.append(format_path("kan_ear_r", "M 198,125 L 200,80 L 210,118 Z"))
        paths.append(format_path("kan_pouch", "M 130,270 Q 160,310 190,270 Z"))
        paths.append(format_path("kan_leg", "M 110,320 Q 70,320 80,350 L 160,350 Q 140,320 110,320 Z"))

    # 53. Platypus (Duck Bill & Beaver Tail)
    elif name == "Platypus":
        paths.append(format_path("pl_body", "M 110,220 C 110,180 250,180 250,220 L 230,290 L 130,290 Z"))
        paths.append(format_path("pl_head", "M 160,160 A 30,30 0 1,1 220,160 Z"))
        paths.append(format_path("pl_bill", "M 175,160 L 150,185 L 175,190 Z"))
        paths.append(format_path("pl_tail", "M 240,250 C 290,250 310,290 240,285 Z"))
        paths.append(format_path("pl_foot_l", "M 115,290 L 100,310 L 135,300 Z"))
        paths.append(format_path("pl_foot_r", "M 225,290 L 240,310 L 205,300 Z"))

    # 54. Wombat (Round Body)
    elif name == "Wombat":
        paths.append(format_path("wom_body", "M 80,210 C 80,140 320,140 320,210 L 290,320 L 110,320 Z"))
        paths.append(format_path("wom_head", "M 155,180 A 35,35 0 1,1 225,180 Z"))
        paths.append(format_path("wom_ear_l", "M 165,152 A 8,8 0 1,0 152,165 Z"))
        paths.append(format_path("wom_ear_r", "M 215,152 A 8,8 0 1,1 228,165 Z"))
        paths.append(format_path("wom_nose", "M 180,195 Q 190,185 200,195 L 195,210 L 185,210 Z"))

    # 55. Tasmanian Devil (Snout & Tail)
    elif name == "Tasmanian Devil":
        paths.append(format_path("td_body", "M 100,230 C 100,175 280,175 280,230 L 260,330 L 120,330 Z"))
        paths.append(format_path("td_head", "M 155,160 A 35,35 0 1,1 225,160 Z"))
        paths.append(format_path("td_ear_l", "M 165,135 L 155,105 L 180,130 Z"))
        paths.append(format_path("td_ear_r", "M 215,135 L 225,105 L 200,130 Z"))
        paths.append(format_path("td_snout", "M 180,180 L 210,180 L 200,195 Z"))
        paths.append(format_path("td_tail", "M 270,270 Q 320,260 310,300 Z"))

    # 56. Giraffe (Extremely Long Neck & Spots)
    elif name == "Giraffe":
        paths.append(format_path("gir_body", "M 90,280 Q 150,250 210,280 L 190,360 L 110,360 Z"))
        paths.append(format_path("gir_neck", "M 150,280 L 195,110 L 160,110 Z"))
        paths.append(format_path("gir_head", "M 165,110 L 205,80 L 215,100 L 185,120 Z"))
        paths.append(format_path("gir_horn_l", "M 175,95 L 170,75 L 178,75 L 180,95 Z"))
        paths.append(format_path("gir_horn_r", "M 190,88 L 190,68 L 198,68 L 195,88 Z"))
        paths.append(format_path("gir_spot1", "M 160,210 Q 175,200 170,225 Z"))
        paths.append(format_path("gir_spot2", "M 168,160 Q 183,150 178,175 Z"))
        paths.append(format_path("gir_spot3", "M 125,300 Q 140,290 135,315 Z"))

    # 57. Zebra (Horse shape with Zebra Stripes)
    elif name == "Zebra":
        paths.append(format_path("zeb_body", "M 90,250 Q 160,220 230,250 L 210,340 L 110,340 Z"))
        paths.append(format_path("zeb_neck", "M 180,250 L 215,160 L 160,190 Z"))
        paths.append(format_path("zeb_head", "M 170,170 L 220,120 L 240,140 L 190,190 Z"))
        paths.append(format_path("zeb_stripe1", "M 180,220 L 160,230 L 180,240 Z"))
        paths.append(format_path("zeb_stripe2", "M 120,270 L 140,270 L 130,285 Z"))
        paths.append(format_path("zeb_stripe3", "M 200,280 L 180,280 L 190,295 Z"))

    # 58. Hippo (Large Snout & Round Body)
    elif name == "Hippo":
        paths.append(format_path("hip_body", "M 80,220 C 80,150 320,150 320,220 L 290,330 L 110,330 Z"))
        paths.append(format_path("hip_head", "M 150,180 A 35,35 0 1,1 220,180 Z"))
        paths.append(format_path("hip_snout", "M 140,205 Q 185,185 230,205 L 220,240 L 150,240 Z"))
        paths.append(format_path("hip_ear_l", "M 160,152 A 6,6 0 1,0 152,160 Z"))
        paths.append(format_path("hip_ear_r", "M 210,152 A 6,6 0 1,1 218,160 Z"))

    # 59. Rhino (Horn on Snout)
    elif name == "Rhino":
        paths.append(format_path("rh_body", "M 80,220 C 80,150 320,150 320,220 L 290,330 L 110,330 Z"))
        paths.append(format_path("rh_head", "M 140,190 A 35,35 0 1,1 210,190 Z"))
        paths.append(format_path("rh_horn1", "M 132,192 L 100,165 L 130,205 Z"))
        paths.append(format_path("rh_horn2", "M 142,190 L 125,180 L 140,202 Z"))
        paths.append(format_path("rh_ear_l", "M 160,162 Q 150,140 162,150 Z"))
        paths.append(format_path("rh_ear_r", "M 190,162 Q 200,140 192,150 Z"))

    # 60. Cheetah (Spots & Face Lines)
    elif name == "Cheetah":
        paths.append(format_path("ch_body", "M 110,230 Q 170,200 230,230 L 215,320 L 125,320 Z"))
        paths.append(format_path("ch_head", "M 160,160 A 30,30 0 1,1 220,160 Z"))
        paths.append(format_path("ch_ear_l", "M 170,135 L 160,115 L 180,132 Z"))
        paths.append(format_path("ch_ear_r", "M 210,135 L 220,115 L 200,132 Z"))
        paths.append(format_path("ch_spot1", "M 140,250 A 4,4 0 1,1 148,250 Z"))
        paths.append(format_path("ch_spot2", "M 190,260 A 4,4 0 1,1 198,260 Z"))
        paths.append(format_path("ch_spot3", "M 160,290 A 4,4 0 1,1 168,290 Z"))
        paths.append(format_path("ch_tear_line_l", "M 180,165 L 185,180"))
        paths.append(format_path("ch_tear_line_r", "M 200,165 L 195,180"))

    # 61. Leopard (Rosettes)
    elif name == "Leopard":
        paths.append(format_path("leo_body", "M 110,230 Q 170,200 230,230 L 215,320 L 125,320 Z"))
        paths.append(format_path("leo_head", "M 160,160 A 30,30 0 1,1 220,160 Z"))
        paths.append(format_path("leo_rosette1", "M 140,250 C 135,245 145,240 148,252 Z"))
        paths.append(format_path("leo_rosette2", "M 190,260 C 185,255 195,250 198,262 Z"))
        paths.append(format_path("leo_rosette3", "M 160,290 C 155,285 165,280 168,292 Z"))

    # 62. Jaguar (Rosettes with Inner Dots)
    elif name == "Jaguar":
        paths.append(format_path("jag_body", "M 110,230 Q 170,200 230,230 L 215,320 L 125,320 Z"))
        paths.append(format_path("jag_head", "M 160,160 A 30,30 0 1,1 220,160 Z"))
        paths.append(format_path("jag_rosette1", "M 140,250 C 135,245 145,240 148,252 Z"))
        paths.append(format_path("jag_rosette1_dot", "M 143,248 A 1.5,1.5 0 1,1 146,248 Z"))
        paths.append(format_path("jag_rosette2", "M 190,260 C 185,255 195,250 198,262 Z"))
        paths.append(format_path("jag_rosette2_dot", "M 193,258 A 1.5,1.5 0 1,1 196,258 Z"))

    # 63. Panther (Sleek Dark Body)
    elif name == "Panther":
        paths.append(format_path("pan_body", "M 110,230 Q 170,200 230,230 L 215,320 L 125,320 Z"))
        paths.append(format_path("pan_head", "M 160,160 A 30,30 0 1,1 220,160 Z"))
        paths.append(format_path("pan_ear_l", "M 170,135 L 160,115 L 180,132 Z"))
        paths.append(format_path("pan_ear_r", "M 210,135 L 220,115 L 200,132 Z"))
        paths.append(format_path("pan_eye_l", "M 178,155 A 2.5,2.5 0 1,1 183,155 Z"))
        paths.append(format_path("pan_eye_r", "M 202,155 A 2.5,2.5 0 1,1 207,155 Z"))

    # 64. Cougar (Sleek Mountain Lion)
    elif name == "Cougar":
        paths.append(format_path("cou_body", "M 110,230 Q 170,200 230,230 L 215,320 L 125,320 Z"))
        paths.append(format_path("cou_head", "M 160,160 A 30,30 0 1,1 220,160 Z"))
        paths.append(format_path("cou_snout", "M 180,175 L 210,175 L 195,190 Z"))

    # 65. Ocelot (Stripes & Rosettes)
    elif name == "Ocelot":
        paths.append(format_path("oce_body", "M 110,230 Q 170,200 230,230 L 215,320 L 125,320 Z"))
        paths.append(format_path("oce_head", "M 160,160 A 30,30 0 1,1 220,160 Z"))
        paths.append(format_path("oce_stripe", "M 165,140 Q 185,145 195,140 Z"))
        paths.append(format_path("oce_spot", "M 140,250 A 3,3 0 1,1 146,250 Z"))

    # 66. Swan (Graceful S-Neck)
    elif name == "Swan":
        paths.append(format_path("sw_body", "M 120,240 C 120,190 260,190 260,240 L 245,290 Q 180,310 135,290 Z"))
        paths.append(format_path("sw_neck", "M 140,240 C 120,160 210,130 190,90 C 215,100 220,160 165,240 Z"))
        paths.append(format_path("sw_head", "M 182,90 A 12,12 0 1,1 206,90 Z"))
        paths.append(format_path("sw_beak", "M 185,92 L 170,105 L 192,100 Z"))
        paths.append(format_path("sw_waves", "M 60,290 Q 140,270 220,290 Q 300,310 380,290 L 380,320 L 60,320 Z"))

    # 67. Flamingo (Long Leg)
    elif name == "Flamingo":
        paths.append(format_path("fla_body", "M 160,180 C 130,200 130,240 180,240 C 230,240 230,200 200,180 Z"))
        paths.append(format_path("fla_neck", "M 170,185 C 160,130 210,100 190,70 C 215,80 220,130 195,185 Z"))
        paths.append(format_path("fla_head", "M 182,70 A 12,12 0 1,1 206,70 Z"))
        paths.append(format_path("fla_beak", "M 188,72 L 180,95 L 195,85 Z"))
        paths.append(format_path("fla_leg_stand", "M 190,240 L 190,360 L 195,360 L 195,240 Z"))
        paths.append(format_path("fla_leg_bend", "M 195,240 L 220,290 L 195,295 Z"))

    # 68. Peacock (Feather Fan)
    elif name == "Peacock":
        # Peacock fan feathers
        paths.append(format_path("pea_fan", "M 200,240 Q 60,190 70,110 Q 200,70 330,110 Q 340,190 200,240 Z"))
        paths.append(format_path("pea_eye1", "M 100,120 A 12,12 0 1,1 124,120 Z"))
        paths.append(format_path("pea_eye2", "M 200,90 A 12,12 0 1,1 224,90 Z"))
        paths.append(format_path("pea_eye3", "M 300,120 A 12,12 0 1,1 324,120 Z"))
        paths.append(format_path("pea_body", "M 180,180 C 170,210 170,270 200,280 C 230,270 230,210 220,180 Z"))
        paths.append(format_path("pea_neck", "M 185,180 C 180,140 220,140 215,110 C 205,110 195,140 195,180 Z"))
        paths.append(format_path("pea_head", "M 200,110 A 10,10 0 1,1 220,110 Z"))

    # 69. Pelican (Throat Pouch & Beak)
    elif name == "Pelican":
        paths.append(format_path("pel_head", "M 170,130 A 20,20 0 1,1 210,130 Z"))
        paths.append(format_path("pel_beak", "M 205,120 L 245,125 Q 235,145 205,140 Z"))
        paths.append(format_path("pel_pouch", "M 205,140 C 215,180 185,210 170,150 Z"))
        paths.append(format_path("pel_body", "M 150,180 C 130,200 130,270 170,275 C 210,270 200,200 180,180 Z"))

    # 70. Seagull (Flying Wings)
    elif name == "Seagull":
        paths.append(format_path("gull_body", "M 180,160 Q 200,145 220,160 Q 200,180 180,160 Z"))
        paths.append(format_path("gull_wing_l", "M 185,152 Q 130,80 100,130 Q 140,140 185,152 Z"))
        paths.append(format_path("gull_wing_r", "M 215,152 Q 270,80 300,130 Q 260,140 215,152 Z"))
        paths.append(format_path("gull_tail", "M 195,172 L 180,195 L 220,195 L 205,172 Z"))

    # 71. Sea Turtle (Shell & Flippers)
    elif name == "Sea Turtle":
        paths.append(format_path("turt_shell", "M 130,220 C 130,170 270,170 270,220 C 270,285 130,285 130,220 Z"))
        paths.append(format_path("turt_scute1", "M 170,210 L 200,195 L 230,210 L 200,230 Z"))
        paths.append(format_path("turt_scute2", "M 170,250 L 200,230 L 230,250 L 200,270 Z"))
        paths.append(format_path("turt_head", "M 180,155 Q 200,120 220,155 Z"))
        paths.append(format_path("turt_flipper_l", "M 140,190 C 80,180 90,230 140,215 Z"))
        paths.append(format_path("turt_flipper_r", "M 260,190 C 320,180 310,230 260,215 Z"))
        paths.append(format_path("turt_leg_l", "M 150,270 L 125,300 L 160,290 Z"))
        paths.append(format_path("turt_leg_r", "M 250,270 L 275,300 L 240,290 Z"))

    # 72. Starfish (5-Pointed Star)
    elif name == "Starfish":
        paths.append(format_path("star_arm1", "M 200,200 L 200,80 L 235,170 Z"))
        paths.append(format_path("star_arm2", "M 200,200 L 310,160 L 255,225 Z"))
        paths.append(format_path("star_arm3", "M 200,200 L 260,310 L 200,250 Z"))
        paths.append(format_path("star_arm4", "M 200,200 L 140,310 L 145,225 Z"))
        paths.append(format_path("star_arm5", "M 200,200 L 90,160 L 165,170 Z"))
        paths.append(format_path("star_core", "M 175,190 A 25,25 0 1,1 225,190 Z"))

    # 73. Crab (Big Claws)
    elif name == "Crab":
        paths.append(format_path("crab_body", "M 130,220 C 130,180 270,180 270,220 C 270,270 130,270 130,220 Z"))
        paths.append(format_path("crab_eye_l", "M 175,180 L 175,160 A 5,5 0 1,1 185,160 L 185,180 Z"))
        paths.append(format_path("crab_eye_r", "M 215,180 L 215,160 A 5,5 0 1,1 225,160 L 225,180 Z"))
        paths.append(format_path("crab_claw_l", "M 140,195 Q 80,180 90,130 Q 120,150 140,195 Z"))
        paths.append(format_path("crab_claw_r", "M 260,195 Q 320,180 310,130 Q 280,150 260,195 Z"))
        paths.append(format_path("crab_leg_l1", "M 135,245 Q 90,265 110,290 L 120,285 Z"))
        paths.append(format_path("crab_leg_r1", "M 265,245 Q 310,265 290,290 L 280,285 Z"))

    # 74. Lobster (Tail & Claws)
    elif name == "Lobster":
        paths.append(format_path("lob_body", "M 160,190 C 160,165 240,165 240,190 L 225,260 L 175,260 Z"))
        paths.append(format_path("lob_claw_l", "M 170,175 Q 110,150 120,95 Q 150,120 170,175 Z"))
        paths.append(format_path("lob_claw_r", "M 230,175 Q 290,150 280,95 Q 250,120 230,175 Z"))
        paths.append(format_path("lob_tail1", "M 180,260 L 170,290 L 230,290 L 220,260 Z"))
        paths.append(format_path("lob_tail2", "M 170,290 L 155,320 L 245,320 L 230,290 Z"))

    # 75. Jellyfish (Dome & Tentacles)
    else:
        paths.append(format_path("jf_dome", "M 130,200 C 130,120 270,120 270,200 L 260,225 L 140,225 Z"))
        paths.append(format_path("jf_tentacle1", "M 160,225 Q 140,270 170,330"))
        paths.append(format_path("jf_tentacle2", "M 185,225 Q 200,270 180,330"))
        paths.append(format_path("jf_tentacle3", "M 215,225 Q 200,270 220,330"))
        paths.append(format_path("jf_tentacle4", "M 240,225 Q 260,270 230,330"))

    return paths

# --- 2. HEROES GENERATOR (Shield of Justice, Swords & Helmet) ---
def generate_hero(index):
    paths = []
    style = index % 2
    cx, cy = 200, 200
    
    if style == 0: # Shield of Justice
        num_sec = 5 + (index % 4) * 2
        ring_width = 25 + (index % 4) * 4
        star_points = 5 + (index % 3)
        
        for ring in range(3):
            r_in = 30 + ring * ring_width
            r_out = r_in + ring_width
            for s in range(num_sec):
                t1 = 2 * math.pi * s / num_sec
                t2 = 2 * math.pi * (s + 1) / num_sec
                
                x1 = cx + r_in * math.cos(t1)
                y1 = cy + r_in * math.sin(t1)
                x2 = cx + r_out * math.cos(t1)
                y2 = cy + r_out * math.sin(t1)
                x3 = cx + r_out * math.cos(t2)
                y3 = cy + r_out * math.sin(t2)
                x4 = cx + r_in * math.cos(t2)
                y4 = cy + r_in * math.sin(t2)
                
                paths.append(format_path(f"shield_r{ring}_s{s}", f"M {x1:.1f},{y1:.1f} L {x2:.1f},{y2:.1f} L {x3:.1f},{y3:.1f} L {x4:.1f},{y4:.1f} Z"))
                
        for s in range(star_points):
            t1 = 2 * math.pi * s / star_points - math.pi/2
            t1_mid = 2 * math.pi * (s + 0.5) / star_points - math.pi/2
            t2 = 2 * math.pi * (s + 1) / star_points - math.pi/2
            
            x1 = cx + 25 * math.cos(t1)
            y1 = cy + 25 * math.sin(t1)
            x_mid = cx + 10 * math.cos(t1_mid)
            y_mid = cy + 10 * math.sin(t1_mid)
            x2 = cx + 25 * math.cos(t2)
            y2 = cy + 25 * math.sin(t2)
            
            paths.append(format_path(f"star_l_{s}", f"M {cx},{cy} L {x1:.1f},{y1:.1f} L {x_mid:.1f},{y_mid:.1f} Z"))
            paths.append(format_path(f"star_r_{s}", f"M {cx},{cy} L {x2:.1f},{y2:.1f} L {x_mid:.1f},{y_mid:.1f} Z"))
            
    else: # Hero Sword & Wings
        blade_w = 12 + (index % 5) * 3
        blade_h = 160 + (index % 10) * 10
        guard_w = 40 + (index % 6) * 8
        num_feathers = 4 + (index % 4)
        
        paths.append(format_path("blade_l", f"M {cx},60 L {cx-blade_w},260 L {cx},260 Z"))
        paths.append(format_path("blade_r", f"M {cx},60 L {cx+blade_w},260 L {cx},260 Z"))
        
        paths.append(format_path("guard_l", f"M {cx-guard_w},{260-blade_w} L {cx},{260} L {cx},275 L {cx-guard_w+10},275 Z"))
        paths.append(format_path("guard_r", f"M {cx+guard_w},{260-blade_w} L {cx},{260} L {cx},275 L {cx+guard_w-10},275 Z"))
        
        paths.append(format_path("handle_l", f"M {cx-6},{275} L {cx},{275} L {cx},340 L {cx-5},340 Z"))
        paths.append(format_path("handle_r", f"M {cx+6},{275} L {cx},{275} L {cx},340 L {cx+5},340 Z"))
        paths.append(format_path("pommel", f"M {cx-15},340 L {cx+15},340 L {cx},360 Z"))
        
        for w in range(num_feathers):
            y_off = 90 + w * 25
            paths.append(format_path(f"wing_l_{w}", f"M {cx-30},{y_off} L {cx-120-w*10},{y_off+20} L {cx-40},{y_off+40} Z"))
            paths.append(format_path(f"wing_r_{w}", f"M {cx+30},{y_off} L {cx+120+w*10},{y_off+20} L {cx+40},{y_off+40} Z"))
            
    return paths

# --- 3. ANIME HEROES GENERATOR (Spiky Hair, Ninja Star / Shuriken) ---
def generate_anime_hero(index):
    paths = []
    style = index % 2
    cx, cy = 200, 200
    
    if style == 0: # Ninja Star (Shuriken)
        num_blades = 3 + (index % 4)
        blade_len = 100 + (index % 6) * 10
        core_r = 12 + (index % 5) * 3
        
        for b in range(num_blades):
            t1 = 2 * math.pi * b / num_blades
            t2 = 2 * math.pi * (b + 0.5) / num_blades
            t3 = 2 * math.pi * (b + 1) / num_blades
            
            x1 = cx + core_r * 2 * math.cos(t1)
            y1 = cy + core_r * 2 * math.sin(t1)
            x_tip = cx + blade_len * math.cos(t2)
            y_tip = cy + blade_len * math.sin(t2)
            x3 = cx + core_r * 2 * math.cos(t3)
            y3 = cy + core_r * 2 * math.sin(t3)
            
            paths.append(format_path(f"shuriken_blade_l_{b}", f"M {cx},{cy} L {x1:.1f},{y1:.1f} L {x_tip:.1f},{y_tip:.1f} Z"))
            paths.append(format_path(f"shuriken_blade_r_{b}", f"M {cx},{cy} L {x3:.1f},{y3:.1f} L {x_tip:.1f},{y_tip:.1f} Z"))
            
        for s in range(4):
            t1 = 2 * math.pi * s / 4
            t2 = 2 * math.pi * (s + 1) / 4
            x1 = cx + core_r * math.cos(t1)
            y1 = cy + core_r * math.sin(t1)
            x2 = cx + core_r * math.cos(t2)
            y2 = cy + core_r * math.sin(t2)
            paths.append(format_path(f"shuriken_core_{s}", f"M {cx},{cy} L {x1:.1f},{y1:.1f} L {x2:.1f},{y2:.1f} Z"))
            
    else: # Spiky Chibi Face
        num_spikes = 6 + (index % 4)
        hair_y = 30 + (index % 6) * 8
        eye_h = 15 + (index % 5) * 3
        jaw_y = 230 + (index % 6) * 6
        
        hair_points = []
        for h in range(num_spikes + 1):
            angle = math.pi + (math.pi * h / num_spikes)
            r = 90 + (h % 2) * 20
            px = cx + r * math.cos(angle)
            py = cy + r * math.sin(angle)
            hair_points.append((px, py))
            
        for h in range(len(hair_points) - 1):
            p1 = hair_points[h]
            p2 = hair_points[h+1]
            mx = (p1[0] + p2[0]) / 2
            my = ((p1[1] + p2[1]) / 2) - 30
            paths.append(format_path(f"hair_spike_{h}", f"M {p1[0]:.1f},{p1[1]:.1f} Q {mx:.1f},{my:.1f} {p2[0]:.1f},{p2[1]:.1f} L {cx},{cy} Z"))
            
        paths.append(format_path("anime_face_l", f"M {cx-80},160 L {cx},{jaw_y} L {cx},140 Z"))
        paths.append(format_path("anime_face_r", f"M {cx+80},160 L {cx},{jaw_y} L {cx},140 Z"))
        
        paths.append(format_path("eye_l_out", f"M {cx-60},170 Q {cx-40},170-{eye_h} {cx-20},170 Q {cx-40},180 {cx-60},170 Z"))
        paths.append(format_path("eye_l_pupil", f"M {cx-48},170 Q {cx-40},170-{eye_h*0.6:.1f} {cx-32},170 Q {cx-40},175 {cx-48},170 Z"))
        paths.append(format_path("eye_r_out", f"M {cx+20},170 Q {cx+40},170-{eye_h} {cx+60},170 Q {cx+40},180 {cx+20},170 Z"))
        paths.append(format_path("eye_r_pupil", f"M {cx+32},170 Q {cx+40},170-{eye_h*0.6:.1f} {cx+48},170 Q {cx+40},175 {cx+32},170 Z"))
        
        paths.append(format_path("mouth", f"M {cx-15},210 Q {cx},210+{(index % 4) * 4} {cx+15},210 Z"))
        
    return paths

# --- 75 Unique Vehicles List ---
VEHICLE_NAMES = [
    "Sports Car", "Sedan", "Coupe", "Limousine", "Convertible", "SUV", "Hatchback", "Crossover", "Supercar", "Vintage Car",
    "Muscle Car", "Race Car", "Taxi", "Police Car", "Ambulance",
    "Scooter", "Cruiser Motorcycle", "Sports Bike", "Dirt Bike", "Touring Bike", "Cafe Racer", "Moped", "Chopper", "Trike", "Quad Bike",
    "Electric Bike", "Vespa", "Custom Chopper", "Motocross", "Superbike",
    "Fighter Jet", "Propeller Biplane", "Commercial Airliner", "Helicopter", "Stealth Bomber", "Cargo Plane", "Glider", "Hot Air Balloon", "Space Shuttle", "Supersonic Jet",
    "Drone", "Seaplane", "Blimp", "Paraglider", "Triplane",
    "Steam Train", "Bullet Train", "Diesel Locomotive", "Cargo Train", "Tram", "Subway Train", "Pickup Truck", "Cargo Truck", "Dump Truck", "Fire Truck",
    "Garbage Truck", "Tow Truck", "Cement Mixer", "Monster Truck", "Tanker Truck",
    "Pirate Ship", "Yacht", "Cruise Ship", "Battleship", "Submarine", "Aircraft Carrier", "Destroyer", "Galleon", "Speedboat", "Sailboat",
    "Canoe", "Tugboat", "Cargo Ship", "Hovercraft", "Catamaran"
]

# --- 4. VEHICLES GENERATOR (75 Unique Cars, Motorcycles, Planes, Trains, Trucks, Ships) ---
def generate_vehicle(index):
    paths = []
    name = VEHICLE_NAMES[index - 1]
    cx, cy = 200, 200

    # --- 1. Cars (Sports Car, Sedan, Coupe, Limousine, Convertible, SUV, etc.) ---
    # Sports Car
    if name == "Sports Car":
        paths.append(format_path("car_roof", "M 130,140 L 230,140 L 260,180 L 100,180 Z"))
        paths.append(format_path("car_body", "M 40,180 L 320,180 L 330,225 L 30,225 Z"))
        paths.append(format_path("car_spoiler", "M 45,180 L 25,160 L 55,160 Z"))
        paths.append(format_path("car_windshield", "M 230,143 L 255,178 L 220,178 Z"))
        paths.append(format_path("car_wheel_f", "M 90,225 A 18,18 0 1,1 126,225 Z"))
        paths.append(format_path("car_wheel_b", "M 240,225 A 18,18 0 1,1 276,225 Z"))

    # Sedan
    elif name == "Sedan":
        paths.append(format_path("sedan_roof", "M 110,130 L 240,130 L 270,180 L 80,180 Z"))
        paths.append(format_path("sedan_body", "M 30,180 L 340,180 L 340,230 L 30,230 Z"))
        paths.append(format_path("sedan_window_f", "M 185,135 L 230,135 L 255,175 L 185,175 Z"))
        paths.append(format_path("sedan_window_b", "M 120,135 L 175,135 L 175,175 L 100,175 Z"))
        paths.append(format_path("sedan_wheel_f", "M 90,230 A 20,20 0 1,1 130,230 Z"))
        paths.append(format_path("sedan_wheel_b", "M 250,230 A 20,20 0 1,1 290,230 Z"))

    # Coupe
    elif name == "Coupe":
        paths.append(format_path("coupe_roof", "M 120,135 L 210,135 L 250,180 L 90,180 Z"))
        paths.append(format_path("coupe_body", "M 40,180 L 310,180 L 320,225 L 30,225 Z"))
        paths.append(format_path("coupe_window", "M 130,140 L 205,140 L 235,175 L 105,175 Z"))
        paths.append(format_path("coupe_wheel_f", "M 85,225 A 18,18 0 1,1 121,225 Z"))
        paths.append(format_path("coupe_wheel_b", "M 235,225 A 18,18 0 1,1 271,225 Z"))

    # Limousine
    elif name == "Limousine":
        paths.append(format_path("limo_roof", "M 90,130 L 270,130 L 290,180 L 70,180 Z"))
        paths.append(format_path("limo_body", "M 20,180 L 350,180 L 350,230 L 20,230 Z"))
        paths.append(format_path("limo_win1", "M 85,135 L 135,135 L 135,175 L 80,175 Z"))
        paths.append(format_path("limo_win2", "M 145,135 L 195,135 L 195,175 L 145,175 Z"))
        paths.append(format_path("limo_win3", "M 205,135 L 265,135 L 278,175 L 205,175 Z"))
        paths.append(format_path("limo_wheel_f", "M 70,230 A 18,18 0 1,1 106,230 Z"))
        paths.append(format_path("limo_wheel_b", "M 280,230 A 18,18 0 1,1 316,230 Z"))

    # Convertible
    elif name == "Convertible":
        paths.append(format_path("conv_body", "M 40,180 L 310,180 L 320,225 L 30,225 Z"))
        paths.append(format_path("conv_windshield", "M 230,140 L 255,180 L 220,180 Z"))
        paths.append(format_path("conv_seat_f", "M 150,180 Q 160,150 170,180 Z"))
        paths.append(format_path("conv_seat_b", "M 100,180 Q 110,150 120,180 Z"))
        paths.append(format_path("conv_wheel_f", "M 85,225 A 18,18 0 1,1 121,225 Z"))
        paths.append(format_path("conv_wheel_b", "M 235,225 A 18,18 0 1,1 271,225 Z"))

    # SUV
    elif name == "SUV":
        paths.append(format_path("suv_roof", "M 100,120 L 250,120 L 250,180 L 80,180 Z"))
        paths.append(format_path("suv_body", "M 30,180 L 330,180 L 330,235 L 30,235 Z"))
        paths.append(format_path("suv_spare_tire", "M 15,190 A 20,20 0 1,1 15,230 Z"))
        paths.append(format_path("suv_wheel_f", "M 95,235 A 22,22 0 1,1 139,235 Z"))
        paths.append(format_path("suv_wheel_b", "M 245,235 A 22,22 0 1,1 289,235 Z"))

    # Hatchback
    elif name == "Hatchback":
        paths.append(format_path("hb_roof", "M 120,130 L 210,130 L 245,180 L 95,180 Z"))
        paths.append(format_path("hb_body", "M 40,180 L 300,180 L 310,225 L 30,225 Z"))
        paths.append(format_path("hb_wheel_f", "M 85,225 A 18,18 0 1,1 121,225 Z"))
        paths.append(format_path("hb_wheel_b", "M 225,225 A 18,18 0 1,1 261,225 Z"))

    # Crossover
    elif name == "Crossover":
        paths.append(format_path("cr_roof", "M 110,125 L 230,125 L 255,180 L 90,180 Z"))
        paths.append(format_path("cr_body", "M 35,180 L 320,180 L 325,230 L 30,230 Z"))
        paths.append(format_path("cr_wheel_f", "M 90,230 A 20,20 0 1,1 130,230 Z"))
        paths.append(format_path("cr_wheel_b", "M 240,230 A 20,20 0 1,1 280,230 Z"))

    # Supercar
    elif name == "Supercar":
        paths.append(format_path("sup_roof", "M 140,145 L 220,145 L 255,180 L 110,180 Z"))
        paths.append(format_path("sup_body", "M 30,180 L 330,180 L 340,220 L 20,220 Z"))
        paths.append(format_path("sup_spoiler", "M 40,180 L 15,155 L 45,155 Z"))
        paths.append(format_path("sup_wheel_f", "M 90,220 A 18,18 0 1,1 126,220 Z"))
        paths.append(format_path("sup_wheel_b", "M 250,220 A 18,18 0 1,1 286,220 Z"))

    # Vintage Car
    elif name == "Vintage Car":
        paths.append(format_path("vin_cabin", "M 130,120 L 220,120 L 230,180 L 120,180 Z"))
        paths.append(format_path("vin_body", "M 60,180 L 300,180 L 300,230 L 60,230 Z"))
        paths.append(format_path("vin_fender_f", "M 240,210 Q 270,170 310,230 Z"))
        paths.append(format_path("vin_fender_r", "M 50,230 Q 90,170 120,210 Z"))
        paths.append(format_path("vin_headlight", "M 300,175 A 10,10 0 1,1 320,185 Z"))
        paths.append(format_path("vin_wheel_f", "M 260,230 A 22,22 0 1,1 304,230 Z"))
        paths.append(format_path("vin_wheel_b", "M 70,230 A 22,22 0 1,1 114,230 Z"))

    # Muscle Car
    elif name == "Muscle Car":
        paths.append(format_path("mus_roof", "M 110,135 L 200,135 L 240,180 L 90,180 Z"))
        paths.append(format_path("mus_body", "M 30,180 L 320,180 L 320,230 L 30,230 Z"))
        paths.append(format_path("mus_hood_scoop", "M 250,172 L 270,172 L 265,180 Z"))
        paths.append(format_path("mus_wheel_f", "M 80,230 A 20,20 0 1,1 120,230 Z"))
        paths.append(format_path("mus_wheel_b", "M 230,230 A 20,20 0 1,1 270,230 Z"))

    # Race Car
    elif name == "Race Car":
        paths.append(format_path("race_body", "M 100,180 L 250,180 L 290,215 L 70,215 Z"))
        paths.append(format_path("race_spoiler", "M 60,180 L 40,130 L 80,130 Z"))
        paths.append(format_path("race_nose_wing", "M 290,215 L 340,215 L 335,225 L 290,225 Z"))
        paths.append(format_path("race_wheel_f", "M 110,215 A 22,22 0 1,1 154,215 Z"))
        paths.append(format_path("race_wheel_b", "M 240,215 A 22,22 0 1,1 284,215 Z"))

    # Taxi
    elif name == "Taxi":
        paths.append(format_path("taxi_roof", "M 110,130 L 240,130 L 270,180 L 80,180 Z"))
        paths.append(format_path("taxi_body", "M 30,180 L 340,180 L 340,230 L 30,230 Z"))
        paths.append(format_path("taxi_sign", "M 160,130 L 190,130 L 185,115 L 165,115 Z"))
        paths.append(format_path("taxi_wheel_f", "M 90,230 A 20,20 0 1,1 130,230 Z"))
        paths.append(format_path("taxi_wheel_b", "M 250,230 A 20,20 0 1,1 290,230 Z"))

    # Police Car
    elif name == "Police Car":
        paths.append(format_path("cop_roof", "M 110,130 L 240,130 L 270,180 L 80,180 Z"))
        paths.append(format_path("cop_body", "M 30,180 L 340,180 L 340,230 L 30,230 Z"))
        paths.append(format_path("cop_siren", "M 165,130 A 8,8 0 1,1 185,130 Z"))
        paths.append(format_path("cop_star", "M 200,195 L 205,205 L 215,205 L 208,212 L 210,222 L 200,215 L 190,222 L 192,212 L 185,205 L 195,205 Z"))
        paths.append(format_path("cop_wheel_f", "M 90,230 A 20,20 0 1,1 130,230 Z"))
        paths.append(format_path("cop_wheel_b", "M 250,230 A 20,20 0 1,1 290,230 Z"))

    # Ambulance
    elif name == "Ambulance":
        paths.append(format_path("amb_box", "M 70,110 L 240,110 L 240,230 L 70,230 Z"))
        paths.append(format_path("amb_cab", "M 240,150 L 320,150 L 335,190 L 335,230 L 240,230 Z"))
        paths.append(format_path("amb_window", "M 255,160 L 305,160 L 315,190 L 255,190 Z"))
        paths.append(format_path("amb_cross_v", "M 145,140 L 165,140 L 165,200 L 145,200 Z"))
        paths.append(format_path("amb_cross_h", "M 125,160 L 185,160 L 185,180 L 125,180 Z"))
        paths.append(format_path("amb_wheel_f", "M 110,230 A 20,20 0 1,1 150,230 Z"))
        paths.append(format_path("amb_wheel_b", "M 260,230 A 20,20 0 1,1 300,230 Z"))


    # --- 2. Motorcycles (Scooter, Cruiser, Sports Bike, Dirt Bike, etc.) ---
    # Scooter
    elif name in ["Scooter", "Vespa"]:
        paths.append(format_path("vespa_shield", "M 240,140 L 260,230 Q 230,260 210,230 Z"))
        paths.append(format_path("vespa_body", "M 100,220 C 130,180 200,210 230,230 L 210,265 L 110,265 Z"))
        paths.append(format_path("vespa_seat", "M 130,195 Q 165,185 190,205 L 180,215 Z"))
        paths.append(format_path("vespa_bars", "M 240,140 L 230,110 L 210,115 Z"))
        paths.append(format_path("vespa_wheel_f", "M 230,265 A 15,15 0 1,1 260,265 Z"))
        paths.append(format_path("vespa_wheel_b", "M 95,265 A 15,15 0 1,1 125,265 Z"))

    # Cruiser Motorcycle
    elif name == "Cruiser Motorcycle":
        paths.append(format_path("cru_frame", "M 100,240 L 170,240 L 230,170 L 150,175 Z"))
        paths.append(format_path("cru_fork", "M 290,245 L 230,120 L 220,125 L 275,245 Z"))
        paths.append(format_path("cru_bars", "M 230,120 L 190,95 L 190,90 Z"))
        paths.append(format_path("cru_tank", "M 160,165 Q 210,150 230,180 L 180,195 Z"))
        paths.append(format_path("cru_seat", "M 115,190 Q 145,180 155,210 Z"))
        paths.append(format_path("cru_wheel_f", "M 270,245 A 25,25 0 1,1 320,245 Z"))
        paths.append(format_path("cru_wheel_b", "M 75,245 A 25,25 0 1,1 125,245 Z"))

    # Chopper / Custom Chopper
    elif name in ["Chopper", "Custom Chopper"]:
        paths.append(format_path("chop_frame", "M 100,240 L 170,240 L 210,180 L 140,185 Z"))
        # Super Long forks
        paths.append(format_path("chop_fork", "M 320,250 L 220,100 L 210,105 L 305,250 Z"))
        # High ape-hanger handlebars
        paths.append(format_path("chop_bars", "M 220,100 L 170,50 L 170,40"))
        paths.append(format_path("chop_tank", "M 150,170 Q 195,160 210,190 L 170,200 Z"))
        paths.append(format_path("chop_wheel_f", "M 300,250 A 20,20 0 1,1 340,250 Z"))
        paths.append(format_path("chop_wheel_b", "M 75,250 A 25,25 0 1,1 125,250 Z"))

    # Quad Bike / Trike
    elif name in ["Quad Bike", "Trike"]:
        paths.append(format_path("quad_body", "M 90,190 L 240,190 L 260,240 L 70,240 Z"))
        paths.append(format_path("quad_bars", "M 210,190 L 190,140 L 170,145 Z"))
        paths.append(format_path("quad_wheel_fl", "M 220,240 A 22,22 0 1,1 264,240 Z"))
        paths.append(format_path("quad_wheel_bl", "M 80,240 A 22,22 0 1,1 124,240 Z"))

    # Generic Motorcycle (Sports Bike, Dirt Bike, Touring, Cafe Racer, Moped, Electric Bike, Motocross, Superbike)
    else:
        paths.append(format_path("mc_frame", "M 90,250 L 160,250 L 220,180 L 130,185 Z"))
        paths.append(format_path("mc_fork", "M 280,250 L 230,130 L 220,135 L 265,250 Z"))
        paths.append(format_path("mc_tank", "M 140,175 Q 190,165 210,195 L 160,205 Z"))
        paths.append(format_path("mc_wheel_f", "M 255,250 A 22,22 0 1,1 299,250 Z"))
        paths.append(format_path("mc_wheel_b", "M 70,250 A 22,22 0 1,1 114,250 Z"))


    # --- 3. Aircrafts (Fighter Jet, Propeller Biplane, Helicopter, Balloon, etc.) ---
    # Fighter Jet
    elif name == "Fighter Jet":
        paths.append(format_path("jet_body", "M 60,200 Q 200,180 340,200 Q 200,220 60,200 Z"))
        paths.append(format_path("jet_wing_l", "M 150,190 L 100,110 L 200,190 Z"))
        paths.append(format_path("jet_wing_r", "M 150,210 L 100,290 L 200,210 Z"))
        paths.append(format_path("jet_cockpit", "M 250,192 Q 280,180 300,200 Q 280,210 250,208 Z"))
        paths.append(format_path("jet_tail_l", "M 80,195 L 50,150 L 70,195 Z"))
        paths.append(format_path("jet_tail_r", "M 80,205 L 50,250 L 70,205 Z"))

    # Propeller Biplane
    elif name in ["Propeller Biplane", "Triplane"]:
        paths.append(format_path("bp_body", "M 80,200 Q 200,175 300,200 Q 200,225 80,200 Z"))
        # Double wings
        paths.append(format_path("bp_wing_top", "M 170,100 L 230,100 L 230,115 L 170,115 Z"))
        paths.append(format_path("bp_wing_bot", "M 170,285 L 230,285 L 230,300 L 170,300 Z"))
        paths.append(format_path("bp_strut_l", "M 180,115 L 180,285"))
        paths.append(format_path("bp_strut_r", "M 220,115 L 220,285"))
        paths.append(format_path("bp_propeller", "M 305,160 L 305,240 M 300,200 L 310,200 Z"))

    # Helicopter
    elif name == "Helicopter":
        paths.append(format_path("heli_cabin", "M 140,160 C 100,180 100,250 140,270 L 240,250 C 260,230 250,180 200,160 Z"))
        paths.append(format_path("heli_window", "M 195,175 L 235,185 L 225,225 L 185,225 Z"))
        paths.append(format_path("heli_tail_boom", "M 140,200 L 60,190 L 60,210 L 140,220 Z"))
        paths.append(format_path("heli_rotor_top", "M 180,160 L 180,135 M 80,135 L 280,135"))
        paths.append(format_path("heli_skid_v1", "M 160,270 L 150,300"))
        paths.append(format_path("heli_skid_v2", "M 210,260 L 200,300"))
        paths.append(format_path("heli_skid_h", "M 120,300 L 240,300"))

    # Hot Air Balloon
    elif name == "Hot Air Balloon":
        paths.append(format_path("hab_balloon", "M 130,160 C 100,80 300,80 270,160 Q 230,230 200,240 Q 170,230 130,160 Z"))
        paths.append(format_path("hab_stripe1", "M 170,105 Q 200,80 230,105 Q 200,240 170,105 Z"))
        paths.append(format_path("hab_basket", "M 185,275 L 215,275 L 210,295 L 190,295 Z"))
        paths.append(format_path("hab_rope_l", "M 180,240 L 185,275"))
        paths.append(format_path("hab_rope_r", "M 220,240 L 215,275"))

    # Space Shuttle
    elif name == "Space Shuttle":
        paths.append(format_path("shuttle_body", "M 175,100 Q 200,50 225,100 L 225,270 L 175,270 Z"))
        paths.append(format_path("shuttle_wing_l", "M 175,180 L 100,270 L 175,270 Z"))
        paths.append(format_path("shuttle_wing_r", "M 225,180 L 300,270 L 225,270 Z"))
        paths.append(format_path("shuttle_booster_l", "M 155,140 L 170,140 L 170,290 L 155,290 Z"))
        paths.append(format_path("shuttle_booster_r", "M 230,140 L 245,140 L 245,290 L 230,290 Z"))

    # Generic Aircraft (Commercial Airliner, Stealth Bomber, Cargo Plane, Glider, Supersonic Jet, Drone, Seaplane, Blimp, Paraglider)
    else:
        paths.append(format_path("air_body", "M 70,200 Q 200,160 320,200 Q 200,230 70,200 Z"))
        paths.append(format_path("air_wing_l", "M 180,185 L 140,90 L 205,180 Z"))
        paths.append(format_path("air_wing_r", "M 180,215 L 140,310 L 205,220 Z"))
        paths.append(format_path("air_tail", "M 100,195 L 60,130 L 85,195 Z"))


    # --- 4. Trucks & Trains (Steam Train, Bullet Train, Fire Truck, Monster Truck, etc.) ---
    # Steam Train
    elif name == "Steam Train":
        paths.append(format_path("train_track", "M 30,290 L 370,290 L 370,295 L 30,295 Z"))
        paths.append(format_path("train_boiler", "M 140,180 L 270,180 L 270,260 L 140,260 Z"))
        paths.append(format_path("train_cab", "M 70,140 L 140,140 L 140,260 L 70,260 Z"))
        paths.append(format_path("train_cab_win", "M 80,155 L 130,155 L 130,200 L 80,200 Z"))
        paths.append(format_path("train_chimney", "M 220,180 L 220,135 L 245,135 L 245,180 Z"))
        paths.append(format_path("train_smoke", "M 232,120 A 10,10 0 1,1 245,110 A 15,15 0 1,1 260,95 Z"))
        paths.append(format_path("train_cowcatcher", "M 270,230 L 310,260 L 270,260 Z"))
        for w, wx in enumerate([90, 150, 210, 260]):
            wr = 16 if w < 2 else 20
            paths.append(format_path(f"train_wheel_{w}", f"M {wx-wr},270 A {wr},{wr} 0 1,1 {wx+wr},270 Z"))

    # Bullet Train
    elif name == "Bullet Train":
        paths.append(format_path("bullet_nose", "M 240,170 L 350,170 Q 300,240 240,240 Z"))
        paths.append(format_path("bullet_body", "M 50,170 L 240,170 L 240,240 L 50,240 Z"))
        paths.append(format_path("bullet_windows", "M 70,180 L 260,180 L 255,200 L 70,200 Z"))

    # Fire Truck
    elif name == "Fire Truck":
        paths.append(format_path("fire_cab", "M 230,150 L 310,150 L 325,190 L 325,250 L 230,250 Z"))
        paths.append(format_path("fire_body", "M 60,140 L 230,140 L 230,250 L 60,250 Z"))
        paths.append(format_path("fire_ladder", "M 80,120 L 220,120 L 220,135 L 80,135 Z"))
        paths.append(format_path("fire_hose", "M 100,170 A 18,18 0 1,1 136,170 Z"))
        paths.append(format_path("fire_wheel_f", "M 100,250 A 20,20 0 1,1 140,250 Z"))
        paths.append(format_path("fire_wheel_b", "M 250,250 A 20,20 0 1,1 290,250 Z"))

    # Monster Truck
    elif name == "Monster Truck":
        paths.append(format_path("mon_cabin", "M 130,130 L 220,130 L 245,175 L 110,175 Z"))
        paths.append(format_path("mon_truck_body", "M 70,175 L 290,175 L 290,225 L 70,225 Z"))
        # Giant wheels
        paths.append(format_path("mon_wheel_f", "M 80,245 A 35,35 0 1,1 150,245 A 35,35 0 1,1 80,245 Z"))
        paths.append(format_path("mon_wheel_b", "M 210,245 A 35,35 0 1,1 280,245 A 35,35 0 1,1 210,245 Z"))

    # Generic Truck (Pickup, Cargo Truck, Dump Truck, Garbage, Tow Truck, Cement Mixer, Tanker)
    else:
        paths.append(format_path("truck_cabin", "M 240,160 L 310,160 L 325,200 L 325,255 L 240,255 Z"))
        paths.append(format_path("truck_cargo", "M 60,130 L 240,130 L 240,255 L 60,255 Z"))
        paths.append(format_path("truck_wheel_f", "M 100,255 A 18,18 0 1,1 136,255 Z"))
        paths.append(format_path("truck_wheel_b", "M 260,255 A 18,18 0 1,1 296,255 Z"))


    # --- 5. Ships & Boats (Pirate Ship, Yacht, Cruise Ship, Battleship, Submarine, etc.) ---
    # Pirate Ship
    elif name == "Pirate Ship":
        paths.append(format_path("pir_waves", "M 30,265 Q 110,245 190,265 Q 270,285 350,265 L 370,300 L 30,300 Z"))
        paths.append(format_path("pir_hull", "M 70,200 L 330,200 L 300,265 L 100,265 Z"))
        paths.append(format_path("pir_mast_c", "M 195,200 L 195,90 L 205,90 L 205,200 Z"))
        paths.append(format_path("pir_sail_c", "M 205,180 L 260,180 Q 230,135 205,100 Z"))
        paths.append(format_path("pir_flag", "M 205,90 L 235,100 L 205,110 Z"))

    # Battleship
    elif name == "Battleship":
        paths.append(format_path("bat_hull", "M 50,210 L 350,210 L 320,265 L 80,265 Z"))
        paths.append(format_path("bat_bridge", "M 140,170 L 250,170 L 260,210 L 130,210 Z"))
        paths.append(format_path("bat_mast", "M 195,170 L 195,110 L 205,110 L 205,170 Z"))
        paths.append(format_path("bat_turret_f", "M 90,195 L 120,195 L 120,210 L 90,210 Z"))
        paths.append(format_path("bat_barrel_f", "M 90,200 L 50,200 L 50,205 L 90,205 Z"))
        paths.append(format_path("bat_turret_b", "M 270,195 L 300,195 L 300,210 L 270,210 Z"))
        paths.append(format_path("bat_barrel_b", "M 300,200 L 340,198 L 340,203 L 300,205 Z"))

    # Submarine
    elif name == "Submarine":
        paths.append(format_path("sub_hull", "M 80,220 C 80,170 320,170 320,220 C 320,270 80,270 80,220 Z"))
        paths.append(format_path("sub_tower", "M 175,170 L 225,170 L 215,190 L 185,190 Z"))
        paths.append(format_path("sub_periscope", "M 195,170 L 195,130 L 215,130 L 215,140 L 205,140 L 205,170 Z"))
        paths.append(format_path("sub_propeller", "M 70,200 L 70,240 M 75,220 L 60,220 Z"))
        paths.append(format_path("sub_windows1", "M 130,220 A 10,10 0 1,1 150,220 Z"))
        paths.append(format_path("sub_windows2", "M 250,220 A 10,10 0 1,1 270,220 Z"))

    # Aircraft Carrier
    elif name == "Aircraft Carrier":
        paths.append(format_path("ac_hull", "M 40,215 L 360,215 L 330,265 L 70,265 Z"))
        paths.append(format_path("ac_deck", "M 30,205 L 350,205 L 360,215 L 40,215 Z"))
        paths.append(format_path("ac_bridge", "M 260,175 L 300,175 L 310,205 L 260,205 Z"))

    # Generic Ship/Boat (Yacht, Cruise Ship, Destroyer, Galleon, Speedboat, Sailboat, Canoe, Tugboat, Cargo Ship, Hovercraft, Catamaran)
    else:
        paths.append(format_path("ship_hull", "M 60,210 L 340,210 L 310,265 L 90,265 Z"))
        paths.append(format_path("ship_mast", "M 195,210 L 195,110 L 205,110 L 205,210 Z"))
        paths.append(format_path("ship_sail", "M 205,190 L 270,190 Q 230,135 205,110 Z"))

    return paths

# --- 5. FLOWERS GENERATOR (Detailed Petals, Blooms) ---
def generate_flower(index):
    paths = []
    num_petals = 5 + (index % 6) * 2
    petal_layers = 2 + (index % 3)
    stem_curve = -20 + (index % 5) * 10
    cx, cy = 200, 200
    
    d_stem = f"M 195,200 Q {195+stem_curve:.1f},300 195,400 L 205,400 Q {205+stem_curve:.1f},300 205,200 Z"
    paths.append(format_path("stem", d_stem))
    
    d_leaf_l = f"M {195+stem_curve/2:.1f},300 C {130+stem_curve/2:.1f},280 120,330 195,350 Z"
    d_leaf_r = f"M {205+stem_curve/2:.1f},320 C {270+stem_curve/2:.1f},300 280,350 205,370 Z"
    paths.append(format_path("leaf_l", d_leaf_l))
    paths.append(format_path("leaf_r", d_leaf_r))
    
    for layer in range(petal_layers):
        r_length = 110 - layer * 22
        r_width = 35 - layer * 6
        
        for p in range(num_petals):
            angle = (2 * math.pi * p / num_petals) + (layer * math.pi / num_petals)
            
            cos_a, sin_a = math.cos(angle), math.sin(angle)
            cos_ortho, sin_ortho = math.cos(angle + math.pi/2), math.sin(angle + math.pi/2)
            
            tip_x = cx + r_length * cos_a
            tip_y = cy + r_length * sin_a
            
            side1_x = cx + (r_length * 0.5) * cos_a + r_width * cos_ortho
            side1_y = cy + (r_length * 0.5) * sin_a + r_width * sin_ortho
            
            side2_x = cx + (r_length * 0.5) * cos_a - r_width * cos_ortho
            side2_y = cy + (r_length * 0.5) * sin_a - r_width * sin_ortho
            
            d = f"M {cx},{cy} Q {side1_x:.1f},{side1_y:.1f} {tip_x:.1f},{tip_y:.1f} Q {side2_x:.1f},{side2_y:.1f} {cx},{cy} Z"
            paths.append(format_path(f"petal_l{layer}_{p}", d))
            
    for s in range(6):
        t1 = 2 * math.pi * s / 6
        t2 = 2 * math.pi * (s + 1) / 6
        x1 = cx + 18 * math.cos(t1)
        y1 = cy + 18 * math.sin(t1)
        x2 = cx + 18 * math.cos(t2)
        y2 = cy + 18 * math.sin(t2)
        d = f"M {cx},{cy} L {x1:.1f},{y1:.1f} L {x2:.1f},{y2:.1f} Z"
        paths.append(format_path(f"center_core_{s}", d))
        
    return paths

# --- 6. MANDALAS GENERATOR (Symmetric Spirograph / Mathematical Mandala) ---
def generate_mandala(index):
    paths = []
    cx, cy = 200, 200
    
    R = 120 + (index % 5) * 8
    r = 30 + (index % 4) * 7
    d = 40 + (index % 6) * 8
    
    gcd_val = math.gcd(R, r)
    laps = r // gcd_val
    steps = 40 * laps
    
    points = []
    for step in range(steps):
        t = 2 * math.pi * step / 40
        x = (R - r) * math.cos(t) + d * math.cos((R - r) * t / r)
        y = (R - r) * math.sin(t) - d * math.sin((R - r) * t / r)
        points.append((cx + x, cy + y))
        
    seg_size = len(points) // 16
    for i in range(16):
        start = i * seg_size
        end = min(len(points) - 1, (i + 1) * seg_size)
        
        path_points = points[start:end+1]
        if len(path_points) < 2:
            continue
            
        d_str = f"M {cx},{cy} L {path_points[0][0]:.1f},{path_points[0][1]:.1f} "
        for pt in path_points[1:]:
            d_str += f"L {pt[0]:.1f},{pt[1]:.1f} "
        d_str += "Z"
        
        paths.append(format_path(f"spiro_outer_s{i}", d_str))
        
    num_sectors = 8 + (index % 3) * 4
    for s in range(num_sectors):
        t1 = 2 * math.pi * s / num_sectors
        t2 = 2 * math.pi * (s + 1) / num_sectors
        x1 = cx + 30 * math.cos(t1)
        y1 = cy + 30 * math.sin(t1)
        x2 = cx + 30 * math.cos(t2)
        y2 = cy + 30 * math.sin(t2)
        paths.append(format_path(f"spiro_inner_s{s}", f"M {cx},{cy} L {x1:.1f},{y1:.1f} L {x2:.1f},{y2:.1f} Z"))
        
    return paths

# --- 7. LANDSCAPES GENERATOR (Mountains, Water Waves, Sun Rays) ---
def generate_landscape(index):
    paths = []
    cx, cy = 200, 100
    
    num_peaks = 3 + (index % 4)
    sun_y = 80 + (index % 5) * 8
    sun_r = 25 + (index % 4) * 4
    wave_frequency = 4 + (index % 3)
    
    for s in range(8):
        t1 = 2 * math.pi * s / 8
        t2 = 2 * math.pi * (s + 1) / 8
        x1 = cx + sun_r * math.cos(t1)
        y1 = sun_y + sun_r * math.sin(t1)
        x2 = cx + sun_r * math.cos(t2)
        y2 = sun_y + sun_r * math.sin(t2)
        
        rx1 = cx + 300 * math.cos(t1)
        ry1 = sun_y + 300 * math.sin(t1)
        rx2 = cx + 300 * math.cos(t2)
        ry2 = sun_y + 300 * math.sin(t2)
        
        rx1_c = max(0.0, min(400.0, rx1))
        ry1_c = max(0.0, min(240.0, ry1))
        rx2_c = max(0.0, min(400.0, rx2))
        ry2_c = max(0.0, min(240.0, ry2))
        
        d = f"M {x1:.1f},{y1:.1f} L {rx1_c:.1f},{ry1_c:.1f} L {rx2_c:.1f},{ry2_c:.1f} L {x2:.1f},{y2:.1f} Z"
        paths.append(format_path(f"sky_ray_{s}", d))
        
    paths.append(format_path("sun_disk", f"M {cx-sun_r},{sun_y} A {sun_r},{sun_r} 0 1,1 {cx+sun_r},{sun_y} A {sun_r},{sun_r} 0 1,1 {cx-sun_r},{sun_y} Z"))
    
    for peak in range(num_peaks):
        px = 40 + peak * (320 // (num_peaks - 1)) + (index % 5) * 4
        py = 110 + (index % 4) * 12 + (peak % 2) * 25
        pw = 70 + (index % 4) * 10
        
        d_left = f"M {px:.1f},{py:.1f} L {px-pw:.1f},240 L {px:.1f},240 Z"
        d_right = f"M {px:.1f},{py:.1f} L {px+pw:.1f},240 L {px:.1f},240 Z"
        paths.append(format_path(f"mountain_{peak}_left", d_left))
        paths.append(format_path(f"mountain_{peak}_right", d_right))
        
    for row in range(4):
        y_top = 240 + row * 40
        y_bot = y_top + 40
        num_segments = 4 + (index % 2)
        for seg in range(num_segments):
            x_start = seg * (400 // num_segments)
            x_end = (seg + 1) * (400 // num_segments)
            x_mid = (x_start + x_end) / 2
            wave_h = 6 + (index % 4) * 3
            d = f"M {x_start:.1f},{y_top:.1f} Q {x_mid:.1f},{y_top - wave_h:.1f} {x_end:.1f},{y_top:.1f} L {x_end:.1f},{y_bot:.1f} Q {x_mid:.1f},{y_bot - wave_h:.1f} {x_start:.1f},{y_bot:.1f} Z"
            paths.append(format_path(f"water_{row}_{seg}", d))
            
    return paths


# --- Generation Loop & Configuration ---
categories = {
    "Animals": (generate_animal, "Animal"),
    "Heroes": (generate_hero, "Shield & Blade"),
    "Anime Heroes": (generate_anime_hero, "Anime Chibi"),
    "Vehicles": (generate_vehicle, "Vehicle"),
    "Flowers": (generate_flower, "Blooms"),
    "Mandalas": (generate_mandala, "Sacred Geometry"),
    "Landscapes": (generate_landscape, "Scenic Vista")
}

# Generate nano_banana for all categories first
for cat in categories.keys():
    kotlin_items.append(f'        CatalogItem("nano_banana", "Nano Banana", "{cat}", "Easy", isPro = false),')

# Generate 75 templates per category
for cat_name, (generator_func, title_prefix) in categories.items():
    for idx in range(1, 76):
        item_id = f"{cat_name.lower().replace(' ', '_')}_temp_{idx}"
        
        if cat_name == "Animals":
            title = ANIMAL_NAMES[idx - 1]
        elif cat_name == "Vehicles":
            title = VEHICLE_NAMES[idx - 1]
        else:
            title = f"{title_prefix} {idx}"
            
        difficulty = ["Easy", "Medium", "Hard"][idx % 3]
        
        # Call generator function
        vector_paths = generator_func(idx)
        filename = f"{item_id}.xml"
        
        # Write asset file
        write_vector_file(filename, vector_paths)
        
        # Register in CatalogItem list
        kotlin_items.append(f'        CatalogItem("{item_id}", "{title}", "{cat_name}", "{difficulty}", isPro = false),')

print(f"Generated {len(kotlin_items)} templates in catalog!")

# Write updated CatalogItem.kt containing all generated templates
kotlin_code = f"""package com.example.coloringbook.core.data.model

data class CatalogItem(
    val id: String,
    val title: String,
    val category: String,
    val difficulty: String,
    val isPro: Boolean = false,
    val isDailyFeatured: Boolean = false
)

object CatalogData {{
    val templates = listOf(
{chr(10).join(kotlin_items)}
    )
}}
"""

with open(r"D:\my app\Coloring Book\core_data\src\main\java\com\example\coloringbook\core\data\model\CatalogItem.kt", "w", encoding="utf-8") as f:
    f.write(kotlin_code)

print("CatalogItem.kt rewritten successfully with 100% unique category templates!")
